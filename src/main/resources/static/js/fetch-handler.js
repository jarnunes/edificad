function postHandler(url, data, successFunction, errorFunction) {
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    }).then(res => {
        let json = res.json();
        if (res.status === 200)
            json.then(data => successFunction(data))
        else
            json.then(data => errorFunction(data))
    }).catch(error => errorFunction({'messages': [error]}));
}

function deleteHandler(url, successFunction, errorFunction) {
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