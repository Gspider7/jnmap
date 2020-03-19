package com.jia.jnmap.nmap.vuln;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import com.gaff.staff.cpe.WFN;
import com.jia.jnmap.nmap.entity.Cpe;
import com.jia.jnmap.nmap.entity.MatchRule;
import com.jia.jnmap.nmap.entity.Ref;
import com.jia.jnmap.nmap.entity.Vulnerability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CveParseHandler {

    private static final Logger logger = LoggerFactory.getLogger(CveParseHandler.class);

    private Resource resource;

    private Vulnerability current;

    private ParseEvent parseEvent;

    public CveParseHandler(Resource resource, ParseEvent parseEvent) {
        this.resource = resource;
        this.parseEvent = parseEvent;
    }

    public void parse() throws IOException {
        JSONReader reader = new JSONReader(new InputStreamReader(resource.getInputStream()));
        reader.startObject();
        while (reader.hasNext()) {
            String key = reader.readString();
            if ("CVE_Items".equals(key)) {
                reader.startArray();
                while (reader.hasNext()) {
                    JSONObject rootObject = (JSONObject) reader.readObject();
                    JSONObject cveObject = rootObject.getJSONObject("cve");
                    JSONObject mateDataObject = cveObject.getJSONObject("CVE_data_meta");
                    JSONObject referencesObject = cveObject.getJSONObject("references");
                    JSONObject configurationsObject = rootObject.getJSONObject("configurations");
                    JSONObject impactObject = rootObject.getJSONObject("impact");

                    current = new Vulnerability();
                    current.setCveId(mateDataObject.getString("ID"));

                    JSONArray referenceDataArray = referencesObject.getJSONArray("reference_data");
                    if (referenceDataArray != null && referenceDataArray.size() > 0) {
                        final ArrayList<Ref> refs = new ArrayList<>();
                        referenceDataArray.forEach(item -> {
                            JSONObject jsonObject = (JSONObject) item;
                            Ref ref = new Ref();
                            ref.setName(jsonObject.getString("name"));
                            ref.setSource(jsonObject.getString("refsource"));
                            ref.setUrl(jsonObject.getString("url"));
                            refs.add(ref);
                        });
                        current.setCveRefs(refs);
                    }

                    JSONArray nodeArray = configurationsObject.getJSONArray("nodes");
                    if (nodeArray != null && nodeArray.size() > 0) {
                        final ArrayList<MatchRule> matchRules = new ArrayList<>();
                        nodeArray.forEach(item -> {
                            JSONObject jsonObject = (JSONObject) item;
                            String operator = jsonObject.getString("operator").toLowerCase();
                            MatchRule matchRule = new MatchRule(operator);
                            if ("or".equals(operator)) {
                                matchRule.setCpeList(getCpeList(jsonObject));
                            } else {
                                JSONArray childrenArray = jsonObject.getJSONArray("children");
                                if (childrenArray != null && childrenArray.size() > 0) {
                                    childrenArray.forEach(item1 -> {
                                        JSONObject jsonObject1 = (JSONObject) item1;
                                        String op = jsonObject1.getString("operator").toLowerCase();
                                        MatchRule childrenMatchRule = new MatchRule(op);
                                        childrenMatchRule.setCpeList(getCpeList(jsonObject1));
                                        matchRule.getChildren().add(childrenMatchRule);
                                    });
                                }
                            }
                            matchRules.add(matchRule);
                        });
                        current.setCveMatchRules(matchRules);
                    }

                    JSONObject baseMetricV2Object = impactObject.getJSONObject("baseMetricV2");
                    if (baseMetricV2Object != null) {
                        JSONObject cvssV2Object = baseMetricV2Object.getJSONObject("cvssV2");
                        current.setCvssBaseScore((int) (cvssV2Object.getDoubleValue("baseScore") * 10));
                        current.setCvssExploitabilityScore((int) (baseMetricV2Object.getDoubleValue("exploitabilityScore") * 10));
                        current.setCvssImpactScore((int) (baseMetricV2Object.getDoubleValue("impactScore") * 10));
                    }
                    try {
                        parseEvent.onOnceEvent(current);
                    } catch (Exception e) {
                        logger.warn("\nCveParseHandler.onOnceEvent Error\nError-Info : {}\nVulnerability : {}\n", e, current);
                    }
                    current = null;
                }
                reader.endArray();
            } else {
                reader.readString();
            }
        }
        reader.endObject();
        reader.close();
    }


    private List<Cpe> getCpeList(JSONObject jsonObject) {
        List<Cpe> list = new ArrayList<>();
        JSONArray cpeMatchArray = jsonObject.getJSONArray("cpe_match");
        if (cpeMatchArray != null && cpeMatchArray.size() > 0) {
            cpeMatchArray.forEach(item -> {
                JSONObject jsonObject1 = (JSONObject) item;
                String cpes = jsonObject1.getString("cpe23Uri");
                if (WFN.isV23(cpes)) {
                    Cpe cpe = new Cpe(cpes);
                    cpe.setStartIncluding(jsonObject1.getString("versionStartIncluding"));
                    cpe.setStartExcluding(jsonObject1.getString("versionStartExcluding"));
                    cpe.setEndIncluding(jsonObject1.getString("versionEndIncluding"));
                    cpe.setEndExcluding(jsonObject1.getString("versionEndExcluding"));
                    list.add(cpe);
                }
            });
        }
        return list;
    }

}
