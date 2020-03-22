package com.jia.jnmap.nmap.vuln;

import com.gaff.staff.cpe.WFN;
import com.jia.jnmap.nmap.entity.Cpe;
import com.jia.jnmap.nmap.entity.MatchRule;
import com.jia.jnmap.nmap.entity.Ref;
import com.jia.jnmap.entity.Vulnerability;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class CnnvdParseHandler extends DefaultHandler {

    private static final Logger logger = LoggerFactory.getLogger(CnnvdParseHandler.class);

    private Vulnerability current;

    private StringBuilder sb;

    private MatchRule matchRule;

    private MatchRule childrenMatchRule;

    private Ref ref;

    private ParseEvent parseEvent;

    public CnnvdParseHandler(ParseEvent parseEvent) {
        this.parseEvent = parseEvent;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        qName = qName.toLowerCase();
        if (qName.equals("entry")) {
            current = new Vulnerability();
        } else if (qName.equals("cncpe")) {
            String operator = attributes.getValue("operator").toLowerCase();
            if ("or".equals(operator) || "and".equals(operator)) {
                matchRule = new MatchRule(operator);
            }
        } else if (qName.equals("cncpe-software") || qName.equals("cncpe-terrace")) {
            String operator = attributes.getValue("operator").toLowerCase();
            if ("or".equals(operator) || "and".equals(operator)) {
                childrenMatchRule = new MatchRule(operator);
            }
        } else if (qName.equals("cncpe-lang")) {
            String name = attributes.getValue("name");
            if (StringUtils.isBlank(name)) {
                return;
            }
            if (!WFN.isV22(name)) {
                logger.warn("cncpe-lang {} is invalid for CPE-22", name);
                return;
            }
            if (childrenMatchRule != null) {
                childrenMatchRule.getCpeList().add(new Cpe(WFN.forURI22(name).toURI23()));
            } else {
                matchRule.getCpeList().add(new Cpe(WFN.forURI22(name).toURI23()));
            }
        } else if (qName.equals("refs")) {
            current.setCnnvdRefs(new ArrayList<>());
        } else if (qName.equals("ref")) {
            ref = new Ref();
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        if (sb == null) {
            sb = new StringBuilder();
        }
        sb.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        qName = qName.toLowerCase();
        if (qName.equals("entry")) {
            try {
                parseEvent.onOnceEvent(current);
            } catch (Exception e) {
                logger.warn("\nCnnvdParseHandler.onOnceEvent Error\nError-Info : {}\nVulnerability : {}\n", e, current);
            }
            current = null;
        } else if (qName.equals("cncpe")) {
            if (current.getCnnvdMatchRules() == null) {
                current.setCnnvdMatchRules(new ArrayList<>());
            }
            if (matchRule != null) {
                current.getCnnvdMatchRules().add(matchRule);
            }
            matchRule = null;
        } else if (qName.equals("cncpe-terrace") || qName.equals("cncpe-software")) {
            if (childrenMatchRule != null) {
                matchRule.getChildren().add(childrenMatchRule);
            }
            childrenMatchRule = null;
        } else if (qName.equals("ref")) {
            current.getCnnvdRefs().add(ref);
            ref = null;
        } else if (StringUtils.isNotBlank(sb.toString())) {
            String text = sb.toString().trim();
            if (qName.equals("name")) {
                current.setName(text);
            } else if (qName.equals("vuln-id")) {
                current.setId(text);
            } else if (qName.equals("published")) {
                current.setPublished(text);
            } else if (qName.equals("modified")) {
                current.setModified(text);
            } else if (qName.equals("source")) {
                current.setSource(text);
            } else if (qName.equals("severity")) {
                current.setSeverity(text);
            } else if (qName.equals("vuln-type")) {
                current.setVulnerabilityType(text);
            } else if (qName.equals("thrtype")) {
                current.setThrtype(text);
            } else if (qName.equals("vuln-descript")) {
                current.setVulnerabilityDesc(text);
            } else if (qName.equals("cve-id")) {
                current.setCveId(text);
            } else if (qName.equals("ref-source")) {
                ref.setSource(text);
            } else if (qName.equals("ref-name")) {
                ref.setName(text);
            } else if (qName.equals("ref-url")) {
                ref.setUrl(text);
            } else if (qName.equals("vuln-solution")) {
                current.setVulnerabilitySolution(text);
            } else if (qName.equals("product")) {
//                if (!WFN.isV22(text)) {
//                    return;
//                }
//                current.addProduct(WFN.forURI22(text).toURI23());
            }
        }
        if (sb != null) {
            sb = null;
        }
    }

}
