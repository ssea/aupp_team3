$(document).ready(async function() {
	const path = window.location.pathname;
	const search = window.location.search;
	LoadingModal();
	try {
		const result = await axios.patch(
			'/user' + path + search
		);
		var bg = 'bg-success bg-opacity-50';
		$('#MessageCard').html(
			'<div class="card ' + bg + '">' +
			'<div class="card-body">' +
			result.data + ' <a href="login" class="btn btn-primary">Login</a>' +
			'</div>' +
			'</div>'
		);
	} catch (error) {
		if (error.response.status === 422) {
			var bg = 'bg-danger bg-opacity-50';
		}
		if (error.response.status === 417) {
			var bg = 'bg-warning bg-opacity-50';
		}
		if (error.response.status === 408) {
			var bg = 'bg-secondary bg-opacity-50';
		}
		$('#MessageCard').html(
			'<div class="card ' + bg + '">' +
			'<div class="card-body">' +
			error.response.data +
			'</div>' +
			'</div>'
		);
	} finally {
		CloseModal();
	}
});

