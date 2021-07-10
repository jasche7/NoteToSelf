import { useState, useEffect } from "react";
import "../App.css";
import { Link } from "react-router-dom";
import { Button, Container } from "reactstrap";
import { withCookies } from "react-cookie";

const Home = (props) => {
  const [isAuthenticated, setAuthenticated] = useState(false);
  const [user, setUser] = useState(undefined);
  const { cookies } = props;
  const [csrfToken] = useState(cookies.get("XSRF-TOKEN"));

  useEffect(() => {
    const checkUser = async () => {
      await fetch("/api/user", { credentials: "include" })
        .then(async (response) => {
          return response.text();
        })
        .then((body) => {
          if (body === "") {
            setAuthenticated(false);
          } else {
            setAuthenticated(true);
            setUser(JSON.parse(body));
          }
        });
    };

    checkUser();
  }, []);

  const login = () => {
    let port = window.location.port ? ":" + window.location.port : "";
    if (port === ":3000") {
      port = ":8080";
    }
    window.location.href = "//" + window.location.hostname + port + "/private";
  };

  const logout = () => {
    fetch("/api/logout", {
      method: "POST",
      credentials: "include",
      headers: { "X-XSRF-TOKEN": csrfToken },
    })
      .then((res) => res.json())
      .then((response) => {
        window.location.href =
          response.logoutUrl +
          "?id_token_hint=" +
          response.idToken +
          "&post_logout_redirect_uri=" +
          window.location.origin;
      });
  };

  const message = user ? (
    <h2>Welcome, {user.name}!</h2>
  ) : (
    <p>Please log in to manage your notes.</p>
  );

  const button = isAuthenticated ? (
    <div>
      <Button color="link">
        <Link to="/notes">Manage Notes</Link>
      </Button>
      <br />
      <Button color="link" onClick={logout}>
        Logout
      </Button>
    </div>
  ) : (
    <Button color="primary" onClick={login}>
      Login
    </Button>
  );

  return (
    <div>
      <Container fluid>
        {message}
        {button}
      </Container>
    </div>
  );
};

export default withCookies(Home);
