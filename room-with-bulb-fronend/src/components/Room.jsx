import React from 'react';
import axios from 'axios';
import { Link, Redirect } from 'react-router-dom';

export default class Room extends React.Component {

  constructor() {
    super();
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  state = {
    id: '',
    name: '',
    country: '',
    bulbOn: ''
  }
  interval = null;

  componentDidMount() {
    this.interval = setInterval(this.getData, 100);
    this.getData();
  }

  getData = () => {
    const { match: { params } } = this.props;

    axios.get(`/rooms/${params.id}`)
      .then(res => {
        const elem = res.data;
        this.setState({ id: elem.id })
        this.setState({ name: elem.name })
        this.setState({ country: elem.country })
        this.setState({ bulbOn: elem.bulbOn })
      })
      .catch((error) => {
        if (error.response.status === 403) this.props.history.push('/403')
        if (error.response.status === 404) this.props.history.push('/404')
      });
  }
  componentWillUnmount() {
    clearInterval(this.interval);
  }

  
  handleSubmit(event) {
    event.preventDefault();
    this.setState({ bulbOn: !this.state.bulbOn })
    axios.post(`/rooms`,
      {
        id: this.state.id,
        name: this.state.name,
        country: this.state.country,
        bulbOn: !this.state.bulbOn,
      })
      .then(res => {
        console.log('result_after_post', res.data)
      })
  }

  render() {
    return (
      <div>
        <ul>
          <Link to="/" style={{ 'backgroundColor': '#326B6F', 'color': 'white', 'textDecoration': 'none' }}>
            Back
          </Link>
        </ul>
        <ul>
          <h2>
            {this.state.name}({this.state.country})
          </h2>
          <form onSubmit={this.handleSubmit}>
            {this.state.error_404 && <Redirect to='/404' />}
            {this.state.bulbOn && <input type="submit" style={{ backgroundColor: 'yellow' }} value="Bulb" />}
            {!this.state.bulbOn && <input type="submit" style={{ backgroundColor: 'gray' }} value="Bulb" />}
          </form>
        </ul>
      </div>);
  }
};