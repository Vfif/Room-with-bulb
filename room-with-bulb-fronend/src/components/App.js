import React from 'react';
import { BrowserRouter, Route } from 'react-router-dom';
import List from './List';
import CreateForm from './CreateForm'
import Room from './Room'
import ResourceNotFound from './ResourceNotFound'
import Forbidden from './Forbidden'

export default function App() {
  return (
    <BrowserRouter>
      <Route path='/' component={List} exact />
      <Route path='/create' component={CreateForm} />
      <Route path='/room/:id(\d+)' component={Room} />
      <Route path='/403' component={Forbidden} />
      <Route path='/404' component={ResourceNotFound} />
    </BrowserRouter>
  );
}