$(document).ready(async function() {
	generateCategoryTable();
});

$(document).on('click', '.delete-btn', async function() {
	Swal.fire({
		title: 'Are you sure to delete the category?',
		html: '<pre>' + "Click on <span class='text-primary fw-bold'>Yes</span> to make a confirmation." + '</pre>',
		icon: 'question',
		showCancelButton: true,
		confirmButtonColor: '#0d6efd',
		cancelButtonColor: '#dc3545',
		confirmButtonText: 'Yes'
	}).then(async (sw) => {
		if (sw.isConfirmed) {
			const tr = $(this).closest('tr');
			const id = tr.attr('id');
			try {
				const result = await axios.delete(
					'/category/delete', {
					params: {
						id: id
					}
				}
				)
				MessageModal('success', 'Delete Succeed', 'Category has been deleted.');
				tr.remove();
			} catch (error) {
				if (error.response.status === 401) {
					return MessageModal('error', 'Server Rejection!', 'Credentials Expired!. Please login again.', () => window.location.reload());
				}
				return MessageModal('error', 'Deletion Error!', 'Can not delete the category!.');
			}
		}
	});
});

async function generateCategoryTable() {
	try {
		const result = await axios.get(
			'/category/getCategories'
		);
		$('#CategoryTable').find('tbody').empty();
		const categories = result.data;
		let row = 1;
		categories.forEach(category => {
			$('#CategoryTable').find('tbody').append(
				'<tr id="' + category.id + '">'
				+ '<td>' + (row++) + '</td>'
				+ '<td>' + category.name + '</td>'
				+ '<td><button class="delete-btn btn btn-sm btn-danger">Delete</button></td>' +
				'</tr>'
			);
		});
	} catch (error) {
		console.log(error)
	}
}

$('#AddBtn').click(function() {
	$('#CategoryModal').modal('show');
});
$('#SaveBtn').click(async function(e) {
	e.preventDefault();
	try {
		const result = await axios.post(
			'/category/create', {}, {
			params: {
				name: $('#name').val()
			}
		});
		generateCategoryTable();
		$('#CategoryModal').modal('hide');
		MessageModal('success', 'Creation Succeed', 'Category has been created.');
	} catch (error) {
		if (error.response.status === 401) {
			return MessageModal('error', 'Server Rejection!', 'Credentials Expired!. Please login again.', () => window.location.reload());
		}
		const errors = error.response.data;
		const inputs = ['name'];
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
$('#CategoryModal').on('shown.bs.modal', function() {
	$('#name').focus();
});
$('#CategoryModal').on('hide.bs.modal', function() {
	$('#name').val('');
	const inputs = ['name'];
	inputs.forEach(input => {
		clearInputError(input)
	});
});