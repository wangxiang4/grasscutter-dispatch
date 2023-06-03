<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>天理原神验证码</title>
</head>
<body>
<table width="100%" style="table-layout:fixed;">
	<tbody>
	<tr>
		<td align="center" valign="top">
			<div>
				<div id="App">
					<img src="https://webstatic.mihoyo.com/upload/event/2021/03/01/ed39ba943da56c88dec2b18f4bfb0a32_2315648370550091638.png" style="width: 120px; height: 30px">
					<div class="text-overlay-title">验证天理原神帐户</div>
					<div class="line"/>
					<div class="text-overlay-secondary">验证码</div>
					<div class="text-captcha-container">
						<a class="text-captcha-container-button">${code}</a>
					</div>
					<div class="text-bottom-website">
						您也可以访问以下网址查看了解最新的信息：<br><a style="color:rgba(0,0,0,0.87);text-decoration:inherit">https://casks.me</a>
					</div>
				</div>
			</div>
		</td>
	</tr>
	</tbody>
</table>
<style>

	.line {
		border-top: 2px solid #ffffff;
	}


	#App {
		position: relative;
		z-index: 0;
		width: 600px;
		height: 375px;
		border-style:solid;
		border-width:thin;
		border-radius:8px;
		padding:40px 20px;
	}

	#App::after {
		content: "";
		background-image: url('https://webstatic.mihoyo.com/upload/op-public/2021/10/03/58574b8c47e058787cc24049413b7cea_7872872512863208275.png');
		background-size: cover;
		background-position: center;
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		opacity: 1; /* 设置图片透明度为 0.7 */
		z-index: -1;
	}

	img {
		display: block;
		margin: -12px auto 0;
	}

	.text-overlay-title {
		font-family:'Google Sans',Roboto,RobotoDraft,Helvetica,Arial,sans-serif;
		color: rgb(255, 255, 255);
		line-height:32px;
		padding-bottom:24px;
		text-align:center;
		word-break:break-word;
		margin-top: 18px;
		margin-bottom: 3px;
		position: relative;
		z-index: 1;
		font-size: 40px;
		font-weight: 800;
		-webkit-text-stroke: 2px black;
		text-stroke: 2px black;
	}

	.text-overlay-secondary {
		font-family:'Google Sans',Roboto,RobotoDraft,Helvetica,Arial,sans-serif;
		color: rgb(255, 255, 255);
		line-height:32px;
		padding-bottom:24px;
		text-align:center;
		word-break:break-word;
		margin-top: 30px;
		position: relative;
		z-index: 1;
		font-size:60px;
		padding-top:20px;
		font-weight: 800;
		-webkit-text-stroke: 2px black;
		text-stroke: 2px black;
	}

	.text-captcha-container {
		padding-top:32px;
		text-align:center;
	}

	.text-captcha-container-button {
		font-weight:800;
		font-size:45px;
		display:inline-block;
		padding:18px 24px;
		color: rgb(255, 255, 255);
		-webkit-text-stroke: 2px black;
		text-stroke: 2px black;
		background-color: #ffffff;
		border-radius:18px;
		letter-spacing: 3px;
		border: 2px solid black;
	}

	.text-bottom-website {
		font-family:'Google Sans',Roboto,RobotoDraft,Helvetica,Arial,sans-serif;
		padding-bottom:24px;
		text-align:center;
		word-break:break-word;
		padding-top:20px;
		font-size:13px;
		line-height:16px;
		color: #3f4246;
		letter-spacing: 2px;
		font-weight: 600;
	}

</style>
</body>
</html>
