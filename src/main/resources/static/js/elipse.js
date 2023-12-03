/* global bootstrap: false */
(() => {
    'use strict'
    const tooltipTriggerList = Array.from(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
    tooltipTriggerList.forEach(tooltipTriggerEl => {
        new bootstrap.Tooltip(tooltipTriggerEl)
    })
})();

const CLASS_MODAL_DELETE = `.${modalDeleteConfirmation}`;
const SAVE_ACTION = 'saveAction'
const SAVE_NEW_ACTION = 'saveNewAction'
const REMOVE_ACTION = 'removeAction'
const EDIT_ACTION = 'editAction'
const SEARCH_KEY = 'search'


/* check/nocheck checkbox */
$('tbody :checkbox').change(function () {
    $(this).closest('tr').toggleClass('selected-row', this.checked);
});

$('thead :checkbox').change(function () {
    $('tbody :checkbox').prop('checked', this.checked).trigger('change');
});

/*show/hide menu*/
$('#menu-button').click(function () {
    $('#menu').toggleClass('hide mobile');
});


// add asterisk to required fields
JSUtils.querySelectorAll('input[required]', selectedItems =>
    selectedItems.forEach(e => e ? e.parentElement.classList.add('required-input') : null))

// masks
JSUtils.querySelector('.phone-mask', selectedElement => {
    selectedElement.addEventListener('keyup', e => e.target.value = formatPhoneNumber(e.target.value))
    selectedElement.addEventListener('change', e => e.target.value = formatPhoneNumber(e.target.value))
    selectedElement.addEventListener('focusin', e => e.target.value = formatPhoneNumber(e.target.value))
})

JSUtils.querySelector('.cep-mask', selectedElement => {
    selectedElement.addEventListener('keyup', e => e.target.value = formatCep(e.target.value))
    selectedElement.addEventListener('change', e => e.target.value = formatCep(e.target.value))
    selectedElement.addEventListener('focusin', e => e.target.value = formatCep(e.target.value))
})

JSUtils.querySelector('.cpf-mask', selectedElement => {
    selectedElement.addEventListener('keyup', e => e.target.value = formatCPF(e.target.value))
    selectedElement.addEventListener('change', e => e.target.value = formatCPF(e.target.value))
    selectedElement.addEventListener('focusin', e => e.target.value = formatCPF(e.target.value))
})

function formatPhoneNumber(value) {
    value = value.replace(/\D/g, "");
    value = value.replace(/^(\d{2})(\d)/g, "($1) $2");
    return value.replace(/(\d)(\d{4})$/, "$1-$2");
}

function formatCep(value) {
    value = value.replace(/\D/g, "");
    value = value.replace(/^(\d{2})(\d)/g, "$1.$2")
    return value.replace(/(\d)(\d{3})$/, "$1-$2");
}
function formatCPF(value) {
    value = value.replace(/\D/g, "");
    return value.replace(/^(\d{3})(\d{3})(\d{3})(\d{2})$/, "$1.$2.$3-$4");
}

// add event listener to '.save-new-btn'
$('.save-new-btn').click(function () {
    $('#entityForm input[name="saveAndNew"]').val("true")
})

// add listener to remove class 'is-invalid' after input something on field
JSUtils.querySelectorAll('.is-invalid', selectedEl =>
    selectedEl.forEach(element =>
        element.addEventListener('keyup', e =>
            JSUtils.nonEmpty(e.target.value, () =>
                e.target.classList.remove('is-invalid')))))


// add event listener on checkbox
JSUtils.querySelectorAll('input[name="selectRow"], input[name="selectAll"]', checkboxes => {
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', function () {
            // disable/enable action buttons
            getSelectedRows(selectedRows => {
                JSUtils.getElementById(EDIT_ACTION, btn => btn.disabled = selectedRows.length !== 1)
                JSUtils.getElementById(REMOVE_ACTION, btn => btn.disabled = selectedRows.length < 1)
            })
        })
    })
})


function getSelectedRows(func) {
    JSUtils.querySelectorAll('input[name="selectRow"]:checked', selectedRows =>
        JSUtils.nonEmpty(selectedRows, it => func(it)))
}

// update objects with ajax
$('#editAction').click(function () {
    let selectedID = getSelectedIds().shift()
    let updatePath = PathURI.createUpdatePath(JSUtils.getCurrentPathResource()) + `/${selectedID}`
    console.log(updatePath)
    redirect(updatePath)
})

// remove objects with ajax
// $('#removeAction').click(function () {
//     let selectedIds = getSelectedIds();
//
//     let paths = new PathURI()
//     // get's the current paths to create delete or
//     paths.deletePathURI = PathURI.createDeletePath(JSUtils.getCurrentPathResource())
//     paths.listPathURI = PathURI.createListPath(JSUtils.getCurrentPathResource())
//     removeFetchHandler(paths, selectedIds, successFetch, errorFetch)
// })

function removeFetchHandler(paths, selectedIds, successFunction, errorFunction) {
    fetch(paths.deletePathURI, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(selectedIds)
    })
        .then(res => res.json())
        .then(res => {
            console.log('exibindo a resposta')
            console.log(res)
            if (res.status_code === 0) {
                successFunction(paths, res.messages[0])
            } else {
                errorFunction(res.messages[0])
            }
        })
        .catch(error => errorFunction(error));
}


function getSelectedIds() {
    let selectedIds = [];
    // get selected row's id
    $("input[name='selectRow']:checked").each(function () {
        selectedIds.push($(this).attr("id"));
    });

    return selectedIds;
}

function redirect(path) {
    window.location.href = window.location.origin + path
}

//----------- begin modal 'Module'
$('#modalModuleForm').submit(function (event) {
    event.preventDefault();
    processFetchPostRequest($(this), event)
})

function postFetchHandler(action, formData, submitterId, afterSuccessProcessSubmittedModalForm) {

    fetch(action, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    })
        .then(res => res.text())
        .then(message => processResponseSubmittedFromModal(message, submitterId, afterSuccessProcessSubmittedModalForm))
        .catch(error => jQueryUtils.addErrorMessageOnModal(error));
}

// Obtém o ID do curso quando o botão "Add Module" é clicado
$('#addModuleAction').click(function () {
    // Obtém o ID do curso no input hidden
    let entityId = $('#entityForm input[type="hidden"]').val();

    // adiciona o id no input hidden do modal
    $('#modalModuleForm input[type="hidden"]').val(entityId)
});

// --------------- end modal module

function processFetchPostRequest(jQueryObject, event) {
    let path = jQueryObject.attr('action')

    // recover clicked button id
    let submitterId = event.originalEvent.submitter.id
    // converts formData to json Object
    let jsonData = {};
    jQueryObject.serializeArray().forEach(function (field) {
        jsonData[field.name] = field.value;
    });

    postFetchHandler(path, jsonData, submitterId, resetModalModuleForm)
}

function resetModalModuleForm() {
    jQueryUtils.resetForm('#modalModuleForm')
}


function modalSubmitterHandler(submitterId, message) {
    if (submitterId === SAVE_ACTION) {
        jQueryUtils.toggleModal('#modalModule')
        reloadTableCurrentPath()
        jQueryUtils.addSuccessMessage(message)
        jQueryUtils.scrollToTop()
    } else if (submitterId === SAVE_NEW_ACTION) {
        jQueryUtils.addSuccessMessageOnModal(message)
    }
}


// module delete
let ecTableContainer = $(".ec-table-container")
ecTableContainer.on('click', ".mini-button-delete", function () {
    let deleteConfirmation = new DeleteConfirmation()

    // get clicked row id
    let row = $(this).closest("tr")
    deleteConfirmation.entityId = row.attr("id")
    deleteConfirmation.entityName = row.find('td:nth-child(2)').text()
    jQueryUtils.append('.modal-container', HTMLUtils.getModalDeleteConfirm(deleteConfirmation))
    jQueryUtils.showModal('.modal-confirm-delete')

    // add listener to cancel button
    $(`.${modalDeleteCancel}`).click(function () {
        jQueryUtils.remove('.modal-confirm-delete')
    })

    // add listener to confirm button
    $(`.${modalDeleteConfirm}`).click(function () {
        modalDeleteDependenteHandler(deleteConfirmation.entityId)
    })
})


function modalDeleteDependenteHandler(elementId) {
    console.log('deletando dependente')
    console.log(elementId)
    onModalDeleteFetchHandler('/dependente/delete/' + elementId, successDeleteHandler)
    reloadTableCurrentPath()
}

function successDeleteHandler() {
    jQueryUtils.toggleModal(`.${modalDeleteConfirmation}`)
    jQueryUtils.remove(`.${modalDeleteConfirmation}`)
    jQueryUtils.scrollToTop()
}

function onModalDeleteFetchHandler(url, successFunction) {
    fetch(url, {
        method: 'DELETE'
    })
        .then(res => res.text())
        .then(message => {
            processResponse(message, successFunction)
        })
        .catch(error => jQueryUtils.addErrorMessage(error));
}

function processResponseSubmittedFromModal(response, submitterId, afterSuccessProcessSubmittedModalForm) {
    let responseJSON = JSON.parse(response)
    let respMessage = responseJSON.messages[0]
    if (responseJSON.statusCode === 0) {
        JSUtils.executeFunction(afterSuccessProcessSubmittedModalForm)
        modalSubmitterHandler(submitterId, respMessage)
    } else {
        jQueryUtils.addErrorMessageOnModal(respMessage)
    }
}

function processResponse(response, successFunction) {
    let responseJSON = JSON.parse(response)
    let respMessage = responseJSON.messages[0]
    if (responseJSON.status_code === 0) {
        JSUtils.executeFunction(successFunction)
        jQueryUtils.addSuccessMessage(respMessage)
    } else {
        jQueryUtils.addErrorMessageOnModal(respMessage)
    }
}

function reloadTableCurrentPath() {
    let currentPath = JSUtils.getCurrentPath()
    $(".ec-table tbody").load(`${currentPath} .ec-table tbody`);
}

// module edit
ecTableContainer.on('click', ".mini-button-edit", function () {
    let row = $(this).closest("tr")
    let entityId = row.attr("id")

    fetch('/module/update/' + entityId)
        .then(response => response.json())
        .then(json => {
            JSUtils.initFormFromJson("#modalModuleForm", json.entity)
            jQueryUtils.showModal('#modalModule')
        })
})

// ======================================= crud delete confirmation
$('#removeAction').click(function () {
    let selectedIds = getSelectedIds();
    let deleteConfirmation = new DeleteConfirmation()

    //TOdo: revisar e deixar as classes css e ids em constantes.
    deleteConfirmation.entityName = `${selectedIds.length} records`
    jQueryUtils.append('.modal-container', HTMLUtils.getModalDeleteConfirm(deleteConfirmation))
    jQueryUtils.showModal(CLASS_MODAL_DELETE)

    // add listener to cancel button
    $(`.${modalDeleteCancel}`).click(function () {
        jQueryUtils.remove(CLASS_MODAL_DELETE)
    })

    // add listener to confirm button
    $(`.${modalDeleteConfirm}`).click(function () {
        let paths = new PathURI()
        // get's the current paths to create delete or
        paths.deletePathURI = PathURI.createDeletePath(JSUtils.getCurrentPathResource())
        paths.listPathURI = PathURI.createListPath(JSUtils.getCurrentPathResource())
        removeFetchHandler(paths, selectedIds, successFetch, errorFetch)
        jQueryUtils.hideModal(CLASS_MODAL_DELETE)
    })
})

function successFetch(paths, respMessage) {
    successDeleteHandler()
    jQueryUtils.addSuccessMessage(respMessage)
    $(".ec-table tbody").load(`${paths.listPathURI} .ec-table tbody`);
}

function errorFetch(respMessage) {
    jQueryUtils.addErrorMessage(respMessage)
    jQueryUtils.hideModal(CLASS_MODAL_DELETE);
    jQueryUtils.remove(CLASS_MODAL_DELETE)
}

// end crud delete confirmation

// $('.create-module').click(function () {
//     let entityId = $('#entityForm input[type="hidden"]').val();
//     fetch('/module/set-course/' + entityId)
//         .then(response => response.json())
//         .then(json => window.location.href = '/module/create')
// })

// importação dos arquivos

$('.select-file').click(function () {

    let inputFile = $('.input-file')
    inputFile.click();

    inputFile.change(function () {
        $('.submit-file').click()
    })
})

// search form
$('#searchForm').submit(function (event) {
    event.preventDefault();

    let arrayQueryString = $(this).serializeArray();

    // get the value of queryString param 'page' to set 0 later
    JSUtils.getQueryParameters().forEach((value, key) => {
        if(key === 'page')
            arrayQueryString.push({name: key, value:0})
    })
    JSUtils.addQueryParametersList(arrayQueryString)
    JSUtils.reloadPage()
});

$(".btn-paginator").click(function (event){
    event.preventDefault();

    let urlClick = event.target.getAttribute('href');

    JSUtils.nonEmptyElse(JSUtils.getQueryParameters().get(SEARCH_KEY),
            value => {
                let url =  urlClick + `&nav=true&${SEARCH_KEY}=${value}`
                window.location.href = urlClick + `&nav=true&${SEARCH_KEY}=${value}`
                // console.log(url)
            },
        () => window.location.href = urlClick)
})
