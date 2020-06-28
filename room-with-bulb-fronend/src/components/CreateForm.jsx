import React from "react";
import axios from 'axios';
import Select from 'react-select'
import countryList from 'react-select-country-list'
import TextField from '@material-ui/core/TextField';

export default class Registration extends React.Component {

  constructor() {
    super();
    this.handleSubmit = this.handleSubmit.bind(this);

    this.options = countryList().getData()

    this.state = {
      options: this.options,
      value: this.options[20],
    }
  }

  changeHandler = value => {
    console.log(value)
    this.setState({ value })
  }

  handleSubmit(event) {
    event.preventDefault();
    axios.post(`/rooms`,
      {
        name: event.target.name.value,
        country: countryList().getLabel(event.target.country.value),
      })
      .then(res => {
        console.log(res.data)
        this.props.history.push('/');
      })

  }

  render() {
    return (
      <form onSubmit={this.handleSubmit}>
        <div>Name:</div>
        <TextField id="name" name="name" variant="outlined" required />
        <div style={{ width: '200px' }}>
          Country:
              <Select id="country" name="country" options={this.state.options}
            value={this.state.value} onChange={this.changeHandler} />
        </div>
        <input type="submit" value="Create" />
      </form>
    );
  }
}