const app = new Vue({
	el: '#app',
	data: {
		file: null,
		message: 'cargue su archivo txt'
	},
	methods: {
		handleFileUpload() {
			this.file = this.$refs.file.files[0];
		},
		submitFile() {
			let formData = new FormData();
			formData.append('file', this.file );

			//call api
			fetch('http://localhost:8080/upload/file', {
				method: 'POST',
				body: formData
			})
			   .then(response => response.json())
			   .then(data => {
					console.log('Success:', data);
				});
		}
	}
});