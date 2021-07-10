import "./App.css";
import Home from "./Components/Home";
import AppNavbar from "./Components/AppNavbar";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import { CookiesProvider } from "react-cookie";
import NoteList from "./Components/NoteList";
import NoteEdit from "./Components/NoteEdit";

const App = () => {
  return (
    <CookiesProvider>
      <Router>
        <AppNavbar />
        <Switch>
          <Route path="/" exact={true} component={Home} />
          <Route path="/notes" exact={true} component={NoteList} />
          <Route path="/note/:id" component={NoteEdit} />
        </Switch>
      </Router>
    </CookiesProvider>
  );
};

export default App;
