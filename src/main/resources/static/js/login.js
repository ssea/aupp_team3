$('#loginBtn').click(async function(e) {
	e.preventDefault();
	try {
		const result = await axios.post(
			'/user/authenticate',
			{
				email: $('#email').val(),
				passwd: $('#passwd').val(),
			}
		);
	} catch (error) {
		throw error;
	}
});