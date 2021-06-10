import React, { useEffect, useState } from "react";
import { Button, ButtonGroup, Container, Table } from "reactstrap";
import AppNavbar from "./AppNavbar";
import { Link, withRouter } from "react-router-dom";
import { instanceOf } from "prop-types";
import { withCookies, Cookies } from "react-cookie";

const NoteList = (props) => {
  const [notes, setNotes] = useState([]);
  const [isLoading, setLoading] = useState(true);
  const { cookies } = props;
  const [csrfToken] = useState(cookies.get("XSRF-TOKEN"));

  useEffect(() => {
    setLoading(true);
    fetch("/api/notes", { credentials: "include" })
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        setNotes(data);
        setLoading(false);
      })
      .catch(() => props.history.push("/"));
  }, [props.history]);

  const remove = async (id) => {
    await fetch(`/api/note/${id}`, {
      method: "DELETE",
      headers: {
        "X-XSRF-TOKEN": csrfToken,
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      credentials: "include",
    }).then(() => {
      let updatedNotes = [...notes].filter((i) => i.id !== id);
      setNotes(updatedNotes);
    });
  };

  const noteList = notes.map((note) => {
    return (
      <tr key={note.id}>
        <td style={{ whiteSpace: "nowrap" }}>{note.title}</td>
        <td>{note.text}</td>
        <td>
          <ButtonGroup>
            <Button
              size="sm"
              color="primary"
              tag={Link}
              to={"/note/" + note.id}
            >
              Edit
            </Button>
            <Button size="sm" color="danger" onClick={() => remove(note.id)}>
              Delete
            </Button>
          </ButtonGroup>
        </td>
      </tr>
    );
  });

  if (isLoading) {
    return <p>Loading...</p>;
  } else
    return (
      <div>
        <AppNavbar />
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/note/new">
              Add Note
            </Button>
          </div>
          <h3>Note to Self</h3>
          <Table className="mt-4">
            <thead>
              <tr>
                <th width="20%">Title</th>
                <th>Text</th>
                <th width="10%">Actions</th>
              </tr>
            </thead>
            <tbody>{noteList}</tbody>
          </Table>
        </Container>
      </div>
    );
};

NoteList.propTypes = {
  cookies: instanceOf(Cookies).isRequired,
};

export default withCookies(withRouter(NoteList));
