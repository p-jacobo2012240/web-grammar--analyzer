// setting default values before uploading the file 
let defaultProps = {
	rawTextList: [{ line: '' }],
	variablesList: [{ character: '' }],
	terminalsList: [{ character: '' }]
};

const app = new Vue({
	el: '#app',
	data() {
		return {
			file: null,
			grammarItem: defaultProps
		}
	},
	methods: {
		handleFileUpload() {
			this.file = this.$refs.file.files[0];
		},
		submitFile() {
			let formData = new FormData();
			formData.append('file', this.file);

			//call api
			fetch('http://localhost:8080/upload/file', {
				method: 'POST',
				body: formData
			})
				.then(response => response.json())
				.then(data => {
					this.grammarItem = data; /// fixed tomorrow
					console.log('Success:', data);
				});
		}
	}
});