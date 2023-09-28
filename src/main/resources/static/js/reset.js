$("#resetBtn").click(async function(e) {
	e.preventDefault();
	LoadingModal();
	try {
		const result = await axios.post(
			'/user/reset',
			{},
			{
				params: {
					email: $('#email').val()
				}
			}
		);
		MessageModal('success', 'Email Send', 'Please check your email for reset password link.', () => window.location.reload());
	} catch (error) {
		const errors = error.response.data;
		const inputs = ['email'];
		for (let index = 0; index < inputs.length; index++) {
			if (errors[inputs[index]] !== null) {
				setInputError(inputs[index], errors[inputs[index]])
			} else {
				clearInputError(inputs[index])
			}
		}
		CloseModal();
	}
})