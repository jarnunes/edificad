/* global bootstrap: false */
(() => {
    'use strict'
    const tooltipTriggerList = Array.from(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
    tooltipTriggerList.forEach(tooltipTriggerEl => {
        new bootstrap.Tooltip(tooltipTriggerEl)
    })
})();


/*show/hide menu*/
$('#menu-button').click(function () {
    $('#menu').toggleClass('hide mobile');
});

JSUtils.querySelectorAll('input[required]', selectedItems =>
    selectedItems.forEach(e => e ? e.parentElement.classList.add('required-input') : null))

// importação dos arquivos

$('.select-file').click(function () {

    let inputFile = $('.input-file')
    inputFile.click();

    inputFile.change(function () {
        $('.submit-file').click()
    })
})
