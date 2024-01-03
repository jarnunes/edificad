// https://select2.org/configuration/options-api

$("#completeBeneficiario").select2(getSettingsSelect2('/api/autocomplete/beneficiarios'));
$("#completeCesta").select2(getSettingsSelect2('/api/autocomplete/cestas'));
$("#completeVoluntario").select2(getSettingsSelect2('/api/autocomplete/voluntarios'));

function getSettingsSelect2(endpoint){
    return {
        theme: "bootstrap-5",
        ajax: {
            url: endpoint,
            dataType: 'json',
            delay: 250,
            debug: true,
            processResults: function (response) {
                return {
                    results: response
                };
            },
            cache: true
        }
    }
}