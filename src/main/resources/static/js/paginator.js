$(".btn-paginator").click(function (event) {
    event.preventDefault();

    let urlClick = event.target.getAttribute('href');

    JSUtils.nonEmptyElse(JSUtils.getQueryParameters().get(SEARCH.key), value =>
            window.location.href = urlClick + `&nav=true&${SEARCH.key}=${value}`,
        () => window.location.href = urlClick)
})


$(document).ready(function () {
    let storedValue = JSUtils.getStoredItem(ROWS_PER_APAGE.storeKey);

    if (storedValue && JSUtils.getQueryParameters().get('size') != null) {
        $(ROWS_PER_APAGE.dClass).val(storedValue);
    }
});

$(ROWS_PER_APAGE.dClass).change(function() {
    let numberRows = $(this).val()

    JSUtils.storeItem(ROWS_PER_APAGE.storeKey, numberRows)
    JSUtils.addQueryParameters('page', 0);
    JSUtils.addQueryParameters('size', numberRows);
    JSUtils.reloadPage()
});