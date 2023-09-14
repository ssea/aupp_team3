<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>SIGN UP</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9"
	crossorigin="anonymous">
</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col-2"></div>
			<div class="col-4">
				<br /> <br /> <br /> <br /> <br />
				<h2>User Register Form</h2>
				<br /> <br />
				
				<div class="alert alert-success center" role="alert">
					<p>${NOTIFICATION}</p>
				</div>

				<form action="<%=request.getContextPath()%>/registerServlet"
					method="post">
					<div class="mb-3">
						<label class="form-label">Name</label> <input type="text"
							class="form-control" name="name" required>
					</div>
					<div class="mb-3">
						<label class="form-label">Email</label> <input type="email"
							class="form-control" name="email" required>
					</div>
					<div class="mb-3">
						<label class="form-label">Phone</label> <input type="text"
							class="form-control" name="phone" required>
					</div>
					<div class="mb-3">
						<label class="form-label">Gender</label> <select
							class="form-select" aria-label="Default select example"
							name="gender" required>
							<option value="" selected>-- Select Gender --</option>
							<option value="Male">Male</option>
							<option value="Female">Female</option>
						</select>
					</div>
					<div class="mb-3">
						<label class="form-label">Password</label> <input type="password"
							class="form-control" name="password" required>
					</div>
					<!--
						<div class="mb-3">					
						<label class="form-label">Password confirm</label> <input type="password"
							class="form-control" name="confirm_password" required>
						</div>
					-->

					<button type="submit" class="btn btn-primary">REGISTER</button>
				</form>

			</div>
			<div class="col-4"></div>
			<div class="col-2"></div>

		</div>
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm"
		crossorigin="anonymous"></script>
</body>
</html>