import "./App.css";
import Home from "./Components/Home";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import NoteList from "./Components/NoteList";

const App = () => {
  return (
    <Router>
      <Switch>
        <Route path="/" exact={true} component={Home} />
        <Route path="/notes" exact={true} component={NoteList} />
      </Switch>
    </Router>
  );
};

export default App;
