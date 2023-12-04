class JSUtils {
    static redirect(path){
        window.location.href = window.location.origin + path
    }
    static nonEmpty(value, func) {
        if (value != null)
            func(value)
    }

    static nonEmptyElse(value, func, elseFunc) {
        if (value != null)
            func(value)
        else
            elseFunc()
    }

    static getElementById(idElement, func) {
        let idEl = document.getElementById(idElement)
        this.nonEmpty(idEl, it => func(it))
    }

    static querySelectorAll(selector, func) {
        let selectedElement = document.querySelectorAll(selector);
        this.nonEmpty(selectedElement, it => func(it))
    }

    static querySelector(selector, func) {
        let selectedElement = document.querySelector(selector);
        this.nonEmpty(selectedElement, it => func(it))
    }

    static isEmpty(value) {
        return value === null || value.trim() === ''
    }

    static isJSONEmpty(json) {
        return json == null || Object.keys(json).length === 0;
    }

    static formToJSON(elements) {
        return [].reduce.call(elements, (data, element) => {
                data[element.name] = element.value;
                return data;
            }, {}
        );
    }

    static existsElement(idElement) {
        return document.contains(document.getElementById(idElement))
    }

    static setStyleDisplayById(idElement, styleDisplay) {
        if (this.existsElement(idElement)) {
            document.getElementById(idElement).style.display = styleDisplay
            return true;
        }
        return false;
    }

    static getQueryParameters() {
        return new URLSearchParams(window.location.search);
    }

    static getQueryParameterByName(paramName) {
        return new Map(Array.from(this.getQueryParameters())
            .filter(([key]) => key === paramName)
            .map(([key, value]) => [key, value]))
    }

    static addQueryParameters(parameter, value) {
        let url = new URL(window.location.href);
        url.searchParams.set(parameter, value)
        this.replaceUrl(url)
    }

    static addQueryParametersList(list) {
        let url = new URL(window.location.href);
        list.forEach(function (param) {
            url.searchParams.set(param.name, param.value);
        });
        this.replaceUrl(url)
    }

    static replaceUrl(newURL) {
        window.history.replaceState({}, '', newURL.toString());
    }

    static reloadPage() {
        location.reload()
    }


    static existsElementByQS(selectors) {
        return document.contains(document.querySelector(`${selectors}`))
    }

    static getElementByQS(selectors) {
        return document.querySelector(`${selectors}`)
    }

    /**
     * Remove element from DOM by ID
     * @param elementId
     * @returns {boolean}
     */
    static removeById(elementId) {
        if (this.existsElement(elementId)) {
            document.getElementById(elementId).remove()
            return true;
        }
        return false;
    }

    /**
     *
     * @param tagName HTML Element Tag
     * @param elementId Element ID
     * @param classList List of CSS Classes
     * @param html HTML Content
     */
    static buildElement(tagName, elementId, classList, html) {
        try {
            let childElement = document.createElement(tagName);
            this.nonEmpty(elementId, eleId => childElement.id = eleId)
            this.nonEmpty(classList, clazzList => childElement.classList.add(...clazzList))
            childElement.innerHTML = html
        } catch (e) {
            throw new Error(e)
        }
    }

    /**
     *
     * @param tagName HTML Element Tag
     * @param elementId Element ID
     * @param classList List of CSS Classes
     * @param html HTML Content
     * @param idContainer Container ID
     * @param appendToBody Append to body or to container
     */
    static appendChild(tagName, elementId, classList, html, idContainer, appendToBody = false) {
        try {
            let childElement = document.createElement(tagName);
            this.nonEmpty(elementId, () => childElement.id = elementId)
            this.nonEmpty(classList, () => childElement.classList.add(...classList))

            childElement.innerHTML = html
            if (idContainer != null && this.existsElement(idContainer)) {
                document.getElementById(idContainer).appendChild(childElement)
            }
            if (appendToBody && idContainer === null) {
                document.body.appendChild(childElement)
            }
        } catch (e) {
            throw new Error(e)
        }
    }

    static appendChildToExistsElement(element, html) {
        element.innerHTML = html
    }

    static replaceBrowserUrl(newUrl) {
        this.nonEmpty(newUrl, () => window.history.pushState({path: newUrl}, '', newUrl))
    }

    static getCurrentPath() {
        return window.location.pathname;
    }

    static getCurrentPathResource() {
        let pathArray = this.getCurrentPath().split("/")
        let cleanedElements = pathArray.filter(elem => !this.isEmpty(elem))
        return cleanedElements[0]
    }

    static initFormFromJson(formId, json) {
        // Preencher os campos do formulário com os dados do JSON
        for (let key in json) {
            let input = $(formId + " input[name='" + key + "']");
            if (input.length) {
                input.val(json[key]);
            }
        }
    }

    static executeFunction(functionName) {
        this.nonEmpty(functionName, nonEmptyFunction => nonEmptyFunction())
    }
}

class jQueryUtils {
    static removeRows(ids) {
        Array.from(ids).forEach(id => jQueryUtils.removeRow(id))
    }

    static removeRow(id) {
        $(`#row_${id}`).remove();
    }

    static addErrorMessages(messages) {
        $('#messages').html(HTMLUtils.getErrorMessage(messages.join('.</br>')))
    }

    static addErrorMessageOnModal(message) {
        $(`.${modalMessagesContainer}`).html(HTMLUtils.getErrorMessage(message))
    }

    static addSuccessMessage(message) {
        $('#messages').html(HTMLUtils.getSuccessMessage(message))
    }

    static addSuccessMessageOnModal(message) {
        $(`.${modalMessagesContainer}`).html(HTMLUtils.getSuccessMessage(message))
    }

    static showModal(idOrClass) {
        $(idOrClass).modal('show')
    }

    static hideModal(idOrClass) {
        $(idOrClass).modal('hide')
    }

    static toggleModal(idOrClass) {
        $(idOrClass).modal('toggle')
    }

    static removeWithTimeout(idOrClass) {
        setTimeout(function () {
            $(idOrClass).remove()
        }, 300)
    }

    static append(idOrClassContainer, html) {
        $(idOrClassContainer).append(html)
    }

    static scrollToTop() {
        $("html, body").animate({scrollTop: 0}, "slow"); // "slow" define a velocidade da animação (opcional)
    }

    static resetForm(idOrClass) {
        let form = $(idOrClass)
        form[0].reset()
    }
}

class SpinnerUtils {
    static showSpinner(idSpinner = 'loading', idContainer = 'container', message = 'Carregando...') {
        JSUtils.appendChild('div', idSpinner, null, HTMLUtils.getSpinner(message), idContainer)
    }

    static hideSpinner(idSpinner = 'loading') {
        JSUtils.removeById(idSpinner)
    }
}

const messagesContainer = 'messages-container'
const modalMessagesContainer = 'modal-messages-container'
const modalDeleteCancel = 'modal-delete-cancel'
const modalDeleteConfirm = 'modal-delete-confirm'
const modalDeleteConfirmation = 'modal-confirm-delete'

class HTMLUtils {
    static getTimeContainerHtml() {
        return ` <div class="times">`
    }

    static addModalToBody(idModalContainer, htmlModal) {
        let divContainerModal = document.createElement('div')
        divContainerModal.id = idModalContainer
        divContainerModal.innerHTML = htmlModal
        document.body.appendChild(divContainerModal)
    }


    static getModalDeleteConfirm(msg) {
        return `
        <div class="modal fade ${CLASS_MODAL_DELETE_CONFIRMATION.className}" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
             aria-labelledby="Delete Confirmation" aria-hidden="true">

            <div class="modal-dialog">
                <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Confirmar Exclusão</h5>
                        </div>
                        <div class="modal-body">
                            <div class="${CLASS_CONTAINER_TO_MODAL_MSG.className}"></div>
                            <div>${msg}</div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary ${CLASS_BTN_CANCEL_MODAL.className}" data-bs-dismiss="modal">
                                <span class="d-none d-md-block">Cancelar</span>
                                <i class="bi bi-x-lg d-block d-md-none"></i>
                            </button>
                            <button type="button" class="btn btn-primary ${CLASS_BTN_CONFIRM_MODAL.className}">
                                Confirmar
                            </button>
                        </div>
                </div>
            </div>
        </div>`
    }

    static getHtmlModal(json, idModal) {
        return `<!-- Modal -->
            <div class="modal fade" id="${idModal}" tabindex="-1" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" >${json.title}</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" onclick="closeModal()"></button>
                        </div>
                        <div class="modal-body">
                            <!-- Include element with django after apend this element in DOM /-->
                        </div>
                    </div>
                </div>
            </div>`
    }

    static getInputRadio(idElement, value) {
        return `
        <label for="${idElement}">
            <input class="form-check-input" name="inputRadios" id="${idElement}" type="radio">
            <span>${value}</span>
        </label>
        `
    }

    /**
     *
     * @param toastId
     * @param bodyMsg String
     * @param showHeader Boolean
     * @param headerTitle String
     * @param headerMsg String
     * @returns {string} Html
     */
    static getHtmlToast(toastId, bodyMsg, showHeader = false, headerTitle, headerMsg) {
        let _headerHtml = `<div class="toast-header">
                                <strong class="me-auto">${headerTitle}</strong>
                                <small>${headerMsg}</small>
                                <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                            </div>`

        return `<div class="toast-container position-fixed bottom-0 end-0 p-3">
                    <div id="${toastId}" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                        ${showHeader ? _headerHtml : ''}
                        <div class="toast-body">
                            ${bodyMsg}
                        </div>
                    </div>
                </div>`
    }

    static getSpinner(message) {
        return `<div class="btn btn-primary position-fixed bottom-0 end-0 p-2 mb-2 me-2" style="cursor: default">
            <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
                ${message === null ? 'Loading...' : message}
            </div> `
    }

    static getMessageAlert(alertType, message) {
        return `<div class="alert text-start alert-${alertType} alert-dismissible fade show" role="alert">
                    <div>
                        <span>${message}</span>
                    </div>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>`
    }

    static getSuccessMessage(message) {
        return this.getMessageAlert('success', message)
    }

    static getErrorMessage(message) {
        return this.getMessageAlert('danger', message)
    }
}

class FetchUtils {
    static fetchHandlerSpinnerGet(url, init, func) {
        init = init || {
            method: 'GET',
            headers: {
                "X-Requested-With": "XMLHttpRequest",
            }
        }
        FetchUtils.fetchHandlerSpinner(url, init, func)
    }

    static fetchHandlerSpinner(url, init, func) {
        SpinnerUtils.showSpinner();

        fetch(url, init)
            .then(json => {
                SpinnerUtils.hideSpinner();
                func(json);
            }).catch(err => {
                SpinnerUtils.hideSpinner();
                console.log(err)
            }
        )
    }

    static fetchHandler(url, init, func) {
        fetch(url, init)
            .then(json => func(json)).catch(err => console.log(err))
    }

    /**
     * addEventListener for element id passed on parameter. After, call a  fetchHandlerGet function.
     * @param elementListenerId element id to add eventListener
     * @param urlPath url path used to concat no queryString
     *
     * ex: If you passed '/blog/', then the complete url was like: http://localhost:8000/blog/?query=xxxxxx
     */
    static eventListenerThenFetchHandlerGet(elementListenerId, urlPath) {
        let search = document.getElementById(elementListenerId)
        search.addEventListener('keypress', (e) => {
            if (e.code === 'Enter') {
                const searchValue = e.target.value;
                const queryString = searchValue === null ? '' : '?query=' + searchValue
                const url = `${urlPath}${queryString}`
                console.log(url)
                FetchUtils.fetchHandlerSpinnerGet(url, null, FetchUtils.afterGetEventListenerThenFetchHandlerGet)
            }
        })
    }

    static afterGetEventListenerThenFetchHandlerGet(json) {
        JSUtils.replaceBrowserUrl(json['url'])
        Jquery.refreshContainer();
    }
}