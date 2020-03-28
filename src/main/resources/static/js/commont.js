$(function () {
    $('#titlestylmlColor').click(function () {
        $(this).toggleClass('titleDivmlbckcolor ')
        $(this).children('button').toggleClass('buttonmlimg ')
        $('#div-styleml').toggle()
    })
    $('#titlestylrwColor').click(function () {
        $(this).toggleClass('titleDivbckcolor ')
        $(this).children('button').toggleClass('buttonTopimg ')
        $('#div-stylerw').toggle()
    })
    $('#titlestylzjColor').click(function () {
        $(this).toggleClass('titleDivbckcolor ')
        $(this).children('button').toggleClass('buttonTopimg ')
        $('#div-stylezj').toggle()
    })
    $('#titlestylldColor').click(function () {
        $(this).toggleClass('titleDivbckcolor ')
        $(this).children('button').toggleClass('buttonTopimg ')
        $('#div-styleld').toggle()
    })
    $('#titlestylbgColor').click(function () {
        $(this).toggleClass('titleDivbckcolor ')
        $(this).children('button').toggleClass('buttonTopimg ')
        $('#div-stylebg').toggle()
    })
    $('#titlestylbzColor').click(function () {
        $(this).toggleClass('titleDivbckcolor ')
        $(this).children('button').toggleClass('buttonTopimg ')
        $('#div-stylebz').toggle()
    })
    $('#tarlirw').click(function () {
        $(this).children('button').toggleClass('buttonTopimg ')
        $('#div-stylerw').toggle()
        $('#titlestylrwColor').toggleClass('titleDivbckcolor')
    })
    $('#tarlizj').click(function () {
        $(this).children('button').toggleClass('buttonTopimg ')
        $('#div-stylezj').toggle()
        $('#titlestylzjColor').toggleClass('titleDivbckcolor')
    })
    $('#tarlild').click(function () {
        $(this).children('button').toggleClass('buttonTopimg ')
        $('#div-styleld').toggle()
        $('#titlestylldColor').toggleClass('titleDivbckcolor')
    })
    $('#tarligc').click(function () {
        $(this).children('button').toggleClass('buttonTopimg ')
        $('#div-stylegc').toggle()
        $('#titlestylgcColor').toggleClass('titleDivbckcolor')
    })
    $('#tarlibz').click(function () {
        $(this).children('button').toggleClass('buttonTopimg ')
        $('#div-stylebz').toggle()
        $('#titlestylbzColor').toggleClass('titleDivbckcolor')
    })
})