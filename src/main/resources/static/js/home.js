$(document).ready(function() {
	const today = new Date();
	const yyyy = today.getFullYear();
	let mm = today.getMonth() + 1; // Months start at 0!
	let dd = today.getDate();

	if (dd < 10) dd = '0' + dd;
	if (mm < 10) mm = '0' + mm;

	const formattedToday = dd + '-' + mm + '-' + yyyy;

	const datePickerOpt = {
		uiLibrary: 'bootstrap5',
		format: 'dd-mm-yyyy',
		value: formattedToday
	}
	$('.date-input').each(function() {
		$(this).datepicker(datePickerOpt);
		if ($(this).parent().find('.invalid-feedback').length === 0) {
			$(this).parent().append('<div class="invalid-feedback"></div>');
		}
	});
	generateExpenseTable()
});


$('#AddBtn').click(async function() {
	await generateCategorySelect();
	$('#ExpenseModal').modal('show');
});

$('#SaveBtn').click(async function(e) {
	e.preventDefault();
	try {
		const result = await axios.post(
			'/expense/create', {}, {
			params: {
				categoryId: $('#CategorySelect option:selected').val(),
				spendAmount: $('#amount').val(),
				currencyType: $('#CurrencyType .currency-type:checked').val(),
				spendDate: $('#spendDate').val(),
				description: $('#description').val()
			}
		});
		generateExpenseTable();
		$('#ExpenseModal').modal('hide');
		MessageModal('success', 'Creation Succeed', 'Expense has been created.');
	} catch (error) {
		if (error.response.status === 401) {
			return MessageModal('error', 'Server Rejection!', 'Credentials Expired!. Please login again.', () => window.location.reload());
		}
		if (error.response.status === 417) {
			return MessageModal('error', 'Expectation Fail!', 'The page is going to refresh!.', () => window.location.reload());
		}
		return MessageModal('error', 'Information Missing!', 'Please fill all the require infomation.');
	}
});

async function generateCategorySelect() {
	try {
		const result = await axios.get(
			'/category/getCategories'
		);
		$('#CategorySelect').empty();
		const categories = result.data;

		categories.forEach(category => {
			$('#CategorySelect').append(
				'<option value="' + category.id + '">' + category.name + '</option>'
			);
		});
	} catch (error) {
		console.log(error)
	}
}
$(document).on('click', '.delete-btn', async function() {
	Swal.fire({
		title: 'Are you sure to delete the Expense record?',
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
					'/expense/delete', {
					params: {
						id: id
					}
				}
				)
				MessageModal('success', 'Delete Succeed', 'Expense record has been deleted.');
				tr.remove();
			} catch (error) {
				if (error.response.status === 401) {
					return MessageModal('error', 'Server Rejection!', 'Credentials Expired!. Please login again.', () => window.location.reload());
				}
				return MessageModal('error', 'Deletion Error!', 'Can not delete the Expense record!.');
			}
		}
	});
});
$('#showDate').change(function() {
	generateExpenseTable();
})
async function generateExpenseTable() {
	try {
		const result = await axios.get(
			'/expense/getAllByDate', {
			params: {
				spendDate: $('#showDate').val(),
			}
		}
		);
		$('#ExpenseTable').find('tbody').empty();
		const expenses = result.data;

		let row = 1;
		expenses.forEach(expense => {
			$('#ExpenseTable').find('tbody').append(
				'<tr id="' + expense.id + '">'
				+ '<td>' + (row++) + '</td>'
				+ '<td>' + expense.category.name + '</td>'
				+ '<td class="' + (expense.type === 'riel' ? 'text-success fw-bold':'') + '">' + expense.amountKhr + '៛</td>'
				+ '<td class="' + (expense.type === 'dollar' ? 'text-success fw-bold':'') + '">' + expense.amountUsd + '$</td>'
				+ '<td>' + expense.description + '</td>'
				+ '<td><button class="delete-btn btn btn-sm btn-danger">Delete</button></td>' +
				'</tr>'
			);
		});
	} catch (error) {
		console.log(error)
	}
}