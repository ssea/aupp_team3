const path = window.location.pathname;
const search = window.location.search;
$(document).ready(async function() {
	LoadingModal();
	try {
		const result = await axios.get(
			'/user/checkToken' + search
		);
		$('form').show();
	} catch (error) {
		$('form').hide();
		$('#MessageCard').html(
			'<div class="card bg-danger bg-opacity-50">' +
			'<div class="card-body">The link is invalided</div>' +
			'</div>'
		);
	} finally {
		CloseModal();
	}
});

$('#setBtn').click(async function(e) {
	e.preventDefault();
	LoadingModal();
	try {
		const result = await axios.patch(
			'/user' + path + search,
			{},
			{
				params: {
					passwd: $('#passwd').val(),
					confirmPasswd: $('#confirmPasswd').val(),
				}
			}
		);
		$('form').hide();
		$('#MessageCard').html(
			'<div class="card bg-success bg-opacity-50">' +
			'<div class="card-body">' +
			result.data + ' <a href="login" class="btn btn-primary">Login</a>' +
			'</div>' +
			'</div>'
		);
	} catch (error) {
		setInputError('passwd', error.response.data);
	} finally {
		CloseModal();
	}
});