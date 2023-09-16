function clearInputError(id_input) {
    $('#' + id_input).removeClass('is-invalid');
    $('#' + id_input).closest('div').find('.invalid-feedback').text('');
}
function setInputError(id_input, text) {
    $('#' + id_input).addClass('is-invalid');
    $('#' + id_input).closest('div').find('.invalid-feedback').text(text);
}

// swal
const LoadingModal = () => {
    Swal.fire({
        text: 'Please wait...',
        allowOutsideClick: false,
        allowEscapeKey: false,
        allowEnterKey: false,
        width: '200px',
    }).then(Swal.showLoading());
}
const MessageModal = (icon, title, text, callback) => {
    $('.popover').remove();
    Swal.fire({
        icon: icon,
        title: title,
        html: '<pre>' + text + '</pre>',
        showConfirmButton: false,
    }).then(() => {
        if (typeof callback === "function") {
            callback();
        }
    })
}
const CloseModal = () => {
    Swal.close();
}
const ErrorModal = (error) => {
    if (error.response.status === 429) {
        return MessageModal('error', 'Server Rejection!', 'Too Many Attempts!\nPlease wait for a moment and try again.');
    }
    if (error.response.status === 401) {
        return MessageModal('error', 'Session Expired!', error.response.data.message, () => window.location.reload());
    }
    if (error.response.status === 404) {
        return MessageModal('error', '404 Not Found!', error.message);
    }
    if (error.response.status === 417) {
        return MessageModal('error', 'Expectation Failed!', error.response.data.message, () => window.location.reload());
    }
    if (error.response.status === 422) {
        return MessageModal('error', 'Action Failed!', error.response.data.message);
    }
    if (error.response.status === 500) {
        return MessageModal('error', 'Something went wrong!', 'Internal server error!.');
    }
}
/// end swal

$(document).ready(async function() {
	try {
		const result = await axios.get(
			'/user/verify'
		);
		const user = result.data;
		$('#UserName').html(user.name);
		$('#UserDisplay').show();
	} catch (error) {
		$('#UserDisplay').hide();
	} 
});