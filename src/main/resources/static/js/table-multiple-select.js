$('tbody :checkbox').change(function () {
    $(this).closest('tr').toggleClass('selected-row', this.checked);
});

$('thead :checkbox').change(function () {
    $('tbody :checkbox').prop('checked', this.checked).trigger('change');
});

// add event listener on checkbox
JSUtils.querySelectorAll('input[name="selectRow"], input[name="selectAll"]', checkboxes => {
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener('change', function () {
            // disable/enable action buttons
            getSelectedRows(selectedRows => {
                JSUtils.querySelector(CLASS_MINI_BTN_EDIT.dClass, btn => btn.disabled = selectedRows.length !== 1)
                JSUtils.querySelector(CLASS_MINI_BTN_DELETE.dClass, btn => btn.disabled = selectedRows.length < 1)
            })
        })
    })
})

function getSelectedRows(func) {
    JSUtils.querySelectorAll('input[name="selectRow"]:checked', selectedRows =>
        JSUtils.nonEmpty(selectedRows, it => func(it)))
}

function getSelectedIds() {
    let selectedIds = [];
    // get selected row's id
    $("input[name='selectRow']:checked").each(function () {
        selectedIds.push($(this).attr("id"));
    });

    return selectedIds;
}
