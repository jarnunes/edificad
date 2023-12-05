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
