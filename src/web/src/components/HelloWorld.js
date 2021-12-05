import React, { Component } from 'react'
import axios from 'axios'
export default class HelloWorld  extends Component{
	state = {id:"",content:""};
    onSubmit = (e) =>{
    	e.preventDefault()
		axios.get(`/api/hello-world?name=${this.state.name}`).then((response)=>{
				//console.log(response.data.id)
				//console.log(response.data.content)
				this.setState({id:response.data.id,content:response.data.content})

		}).catch ((error)=>{
			console.log("Error in getting getting response",error);
		});
	}
	onChange = (e)=>{
    	this.setState({[e.target.name]:e.target.value});
    	//this.setState({name:e.target.value});
	}
	render() {
        return (
            <div>
                <h1>Hello World</h1>
				<form onSubmit={this.onSubmit}>
					<label>Name </label>
					<input type="text" name="name" value={this.state.name} onChange={this.onChange}/><br/>
					<input type="submit"  value="Enter"/>
				</form>
				{this.state.id &&
					<>
						<p>ID : </p> <p>{this.state.id}</p>
						<p>Content : </p><p>{this.state.content}</p>
					</>
				}
            </div>
        );
    }
  }