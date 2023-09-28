$("#registerBtn").click(async function(e) {
	e.preventDefault();
	LoadingModal();
	try {
		const result = await axios.post(
			'/user/register',
			{},
			{
				params: {
					name: $('#name').val(),
					email: $('#email').val(),
					passwd: $('#passwd').val(),
					confirmPasswd: $('#confirmPasswd').val(),
				}
			}
		);
		MessageModal('success', 'Registration Succeed', 'Please check your email for activation link.', () => window.location.reload());
	} catch (error) {
		const errors = error.response.data;
		const inputs = ['name', 'email', 'passwd'];
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