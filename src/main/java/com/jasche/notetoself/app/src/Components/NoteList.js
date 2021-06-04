import React, { useEffect, useState } from "react";
import { Button, ButtonGroup, Container, Table } from "reactstrap";
import AppNavbar from "./AppNavbar";
import { Link } from "react-router-dom";

const NoteList = () => {
  const [notes, setNotes] = useState([]);
  const [isLoading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(true);
    fetch("/api/notes")
      .then((response) => response.json())
      .then((data) => {
        setNotes(data);
        setLoading(false);
      });
  }, []);

  const remove = async (id) => {
    await fetch(`/api/notes/${id}`, {
      method: "DELETE",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
    }).then(() => {
      let updatedNotes = [...notes].filter((i) => i.id !== id);
      setNotes(updatedNotes);
    });
  };

  const noteList = notes.map((note) => {
    return (
      <tr key={note.id}>
        <td style={{ whiteSpace: "nowrap" }}>{note.title}</td>
        <td>{note.author}</td>
        <td>{note.text}</td>
        <td>
          <ButtonGroup>
            <Button
              size="sm"
              color="primary"
              tag={Link}
              to={"/notes/" + note.id}
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
            <Button color="success" tag={Link} to="/notes/new">
              Add Note
            </Button>
          </div>
          <h3>Note to Self</h3>
          <Table className="mt-4">
            <thead>
              <tr>
                <th width="20%">Title</th>
                <th width="20%">Author</th>
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

export default NoteList;
