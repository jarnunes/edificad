JSUtils.querySelector('.phone-mask', selectedElement => {
    Array.from(['keyup', 'change', 'focusin']).forEach(ev =>
        selectedElement.addEventListener(ev, e => e.target.value = formatPhoneNumber(e.target.value)))
})

JSUtils.querySelector('.cep-mask', selectedElement => {
    Array.from(['keyup', 'change', 'focusin']).forEach(ev =>
        selectedElement.addEventListener(ev, e => e.target.value = formatCEP(e.target.value)))
})

JSUtils.querySelector('.cpf-mask', selectedElement => {
    Array.from(['keyup', 'change', 'focusin']).forEach(ev =>
     selectedElement.addEventListener(ev, e => e.target.value = formatCPF(e.target.value)))
})

function formatPhoneNumber(value) {
    value = value.replace(/\D/g, "");
    value = value.replace(/^(\d{2})(\d)/g, "($1) $2");
    return value.replace(/(\d)(\d{4})$/, "$1-$2");
}

function formatCEP(value) {
    value = value.replace(/\D/g, "");
    value = value.replace(/^(\d{2})(\d)/g, "$1.$2")
    return value.replace(/(\d)(\d{3})$/, "$1-$2");
}

function formatCPF(value) {
    value = value.replace(/\D/g, "");
    return value.replace(/^(\d{3})(\d{3})(\d{3})(\d{2})/g, "$1.$2.$3-$4");
}