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

    JSUtils.nonEmptyElse(JSUtils.getQueryParameters().get(SEARCH.key),
            value => {
                let url =  urlClick + `&nav=true&${SEARCH.key}=${value}`
                window.location.href = urlClick + `&nav=true&${SEARCH.key}=${value}`
                // console.log(url)
            },
        () => window.location.href = urlClick)
})
