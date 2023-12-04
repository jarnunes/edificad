/*show/hide menu*/
$('#menu-button').click(function () {
    $('#menu').toggleClass('hide mobile');
});

// JSUtils.querySelectorAll('input[required]', selectedItems =>
//     selectedItems.forEach(e => e ? e.parentElement.classList.add('required-input') : null))
document.querySelectorAll('input[required]').forEach(e => {
    const parentDiv = e.closest('.col');
    if (parentDiv) {
        const labelElement = parentDiv.querySelector('label[for="' + e.id + '"]');
        if (labelElement) {
            labelElement.classList.add('required-input');
        }
    }
})
document.querySelectorAll('select[required]').forEach(e => {
    const parentDiv = e.closest('.col');
    if (parentDiv) {
        const labelElement = parentDiv.querySelector('label[for="' + e.id + '"]');
        if (labelElement) {
            labelElement.classList.add('required-input');
        }
    }
})

// importação dos arquivos

$('.select-file').click(function () {

    let inputFile = $('.input-file')
    inputFile.click();

    inputFile.change(function () {
        $('.submit-file').click()
    })
})
