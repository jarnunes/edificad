class RequestInfo {
    constructor() {
        this._url = null;
        this._data = {};
    }

    get url() {
        return this._url;
    }

    set url(value) {
        this._url = value;
    }

    get data() {
        return this._data;
    }

    set data(value) {
        this._data = value;
    }
}

class PathInfo {

    static requestUpdateURL(pathVariable){
        return `${JSUtils.getCurrentPath()}/update/${pathVariable}`
    }

    static requestPostDeleteURL(){
        return `${JSUtils.getCurrentPath()}/delete`
    }

    static createDeletePath(resource) {
        return `/${resource}/delete`
    }

    static createListPath(resource) {
        return `/${resource}`
    }

    static createUpdatePath(resource) {
        return `/${resource}/update`
    }

    static createSavePath(resource) {
        return `/${resource}/save`
    }
}