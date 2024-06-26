// setting default values before uploading the file 
let defaultProps = {
	rawTextList: [{ line: '' }],
	variablesList: [{ character: '' }],
	terminalsList: [{ character: '' }],
	variablesListComplex: [{ character: '' }],
	terminalsListComplex: [{ character: '' }],
	rawTextWithoutRecursionToLeft: [{ line: '' }],
	terminalsListWithoutRecursionToLeft: [{ line: '' }],
	variablesListComplexWithoutRecursionToLeft: [{ character: '' }],
    terminalsListComplexWithoutRecursionToLeft: [{ character: '' }],
    variableFirstFunctionList: [{ character: '' }],
    variableFollowFunctionList: [{ character: '' }],
    terminalFirstFunctionList: [{ character: '' }],
    terminalFollowFunctionList: [{ character: '' }]
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

			//auto  submitFile called
			this.submitFile();
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
					this.grammarItem = data; 
					console.log('Success:', data);
					
					if(data.status !== undefined ) {
						/// console.log('el txt es invalido.....');
						let conf = confirm("El vector de variables o terminales no cumple con los requisitos");
						console.log('status ', conf )	
					}
					
				});
		}
	}
});