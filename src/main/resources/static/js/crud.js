// begin edit crud
$(CLASS_MINI_BTN_EDIT.dClass).click(function () {
    let selectedID = getSelectedIds().shift()
    console.log(PathInfo.requestUpdateURL(selectedID))
     JSUtils.redirect(PathInfo.requestUpdateURL(selectedID))
});
// end edit crud

// begin delete crud
$(CLASS_MINI_BTN_DELETE.dClass).click(function () {
    let selectedIds = getSelectedIds();
    let deleteMessage = `Os registros selecionados serão removidos. Confirma a exclusão de ${selectedIds.length} registro(s) ?`

    jQueryUtils.append(CLASS_CONTAINER_TO_MODAL.dClass, HTMLUtils.getModalDeleteConfirm(deleteMessage))
    jQueryUtils.showModal(CLASS_MODAL.dClass)

    // add listener to cancel button
    $(CLASS_BTN_CANCEL_MODAL.dClass).click(function () {
        jQueryUtils.hideModal(CLASS_MODAL.dClass)
        jQueryUtils.removeWithTimeout(CLASS_MODAL.dClass)
    })

    // add listener to confirm button
    $(CLASS_BTN_CONFIRM_MODAL.dClass).click(function () {
        let url = PathInfo.requestPostDeleteURL()
        postHandler(url, selectedIds, successCrudDeleteHandler, errorCrudDeleteHandler)
    });
});

function successCrudDeleteHandler(json) {
    jQueryUtils.addSuccessMessage(json.messages)
    jQueryUtils.removeRows(json.data)
    jQueryUtils.hideModal(CLASS_MODAL.dClass)
    jQueryUtils.removeWithTimeout(CLASS_MODAL.dClass)
}

function errorCrudDeleteHandler(json) {
    jQueryUtils.addErrorMessages(json.messages)
    jQueryUtils.hideModal(CLASS_MODAL.dClass);
    jQueryUtils.removeWithTimeout(CLASS_MODAL.dClass)
    jQueryUtils.removeRows(json.data)
}

// end delete crud

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
        deleteRow(id)
        removeDeletedRow(id)
    })
})

function deleteRow(elementId) {
    deleteHandler('/dependente/delete/' + elementId, successDependenteDeleteHandler, errorDependenteDeleteHandler)
}

function removeDeletedRow(elementId) {
    $(`#${elementId}`).remove()
}

function successDependenteDeleteHandler(response) {
    jQueryUtils.addSuccessMessage(response.messages)
    jQueryUtils.toggleModal(CLASS_MODAL.dClass)
    jQueryUtils.removeWithTimeout(CLASS_MODAL.dClass)
    jQueryUtils.scrollToTop()
}

function errorDependenteDeleteHandler(response) {
    jQueryUtils.addErrorMessageOnModal(response.messages)
}

// end delete dependentes
