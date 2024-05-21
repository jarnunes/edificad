
// begin delete dependentes
$(".ec-table-container").on('click', CLASS_MINI_BTN_DELETE_ROW.dClass, function () {

    // get clicked row id
    let row = $(this).closest("tr")
    let id = row.attr("id")
    let entityName = row.find('td:nth-child(2)').text()
    let messageModal = `Confirmar exclusão do dependente "${entityName}"?`
    jQueryUtils.append(CLASS_CONTAINER_TO_MODAL.dClass, HTMLUtils.getModalDeleteConfirm(messageModal));
    jQueryUtils.showModal(CLASS_MODAL.dClass)

    // add listener to cancel button
    $(CLASS_BTN_CANCEL_MODAL.dClass).click(function () {
        jQueryUtils.removeWithTimeout(CLASS_MODAL.dClass)
    })

    // add listener to confirm button
    $(CLASS_BTN_CONFIRM_MODAL.dClass).click(function () {
        fetchDeleteHandler('/dependente/delete/' + id, successDependenteDeleteHandler, errorDependenteDeleteHandler)
        removeDeletedRow(id)
    })
})

function fetchDeleteHandler(url, successFunction, errorFunction) {
    fetch(url, {
        method: 'DELETE'
    }).then(res => {
        let json = res.json();
        if (res.status === 200)
            json.then(data => successFunction(data))
        else
            json.then(data => errorFunction(data))
    }).catch(error => errorFunction({'messages': [error]}));
}

function removeDeletedRow(elementId) {
    $(`#${elementId}`).remove()
}

function successDependenteDeleteHandler(response) {
    jQueryUtils.addSuccessMessage(response.messages)
    jQueryUtils.toggleModal(CLASS_MODAL.dClass)
    jQueryUtils.removeWithTimeout(CLASS_MODAL.dClass)
    jQueryUtils.scrollToTop();
}

function errorDependenteDeleteHandler(response) {
    jQueryUtils.addErrorMessageOnModal(response.messages)
}