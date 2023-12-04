$(".btn-paginator").click(function (event) {
    event.preventDefault();

    let urlClick = event.target.getAttribute('href');

    JSUtils.nonEmptyElse(JSUtils.getQueryParameters().get(SEARCH.key), value =>
            window.location.href = urlClick + `&nav=true&${SEARCH.key}=${value}`,
        () => window.location.href = urlClick)
})
