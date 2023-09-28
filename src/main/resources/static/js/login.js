$('#loginBtn').click(async function(e) {
	e.preventDefault();
	LoadingModal();
	try {
		const result = await axios.post(
			'/user/login',
			{},
			{
				params: {
					email: $('#email').val(),
					passwd: $('#passwd').val(),
				}
			}
		);
		window.location.href = "";
	} catch (error) {
		const errors = error.response.data;
		const inputs = ['email', 'passwd'];
		for (let index = 0; index < inputs.length; index++) {
			if (errors[inputs[index]] !== null) {
				setInputError(inputs[index], errors[inputs[index]])
			} else {
				clearInputError(inputs[index])
			}
		}
		CloseModal();
	}
});