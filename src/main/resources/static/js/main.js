function clearInputError(id_input) {
    $('#' + id_input).removeClass('is-invalid');
    $('#' + id_input).closest('div').find('.invalid-feedback').text('');
}
function setInputError(id_input, text) {
    $('#' + id_input).addClass('is-invalid');
    $('#' + id_input).closest('div').find('.invalid-feedback').text(text);
}