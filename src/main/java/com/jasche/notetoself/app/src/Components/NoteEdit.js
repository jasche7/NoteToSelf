import { useState, useEffect } from "react";
import { Link, withRouter } from "react-router-dom";
import { Button, Container, Form, FormGroup, Input, Label } from "reactstrap";
import AppNavbar from "./AppNavbar";

const NoteEdit = (props) => {
  const emptyEntry = {
    title: "",
    author: "",
    text: "",
  };
  const [entry, setEntry] = useState(emptyEntry);

  const handleChange = (event) => {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = { ...entry };
    item[name] = value;
    setEntry(item);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    await fetch("/api/note" + (entry.id ? "/" + entry.id : ""), {
      method: entry.id ? "PUT" : "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify(entry),
    });
    props.history.push("/notes");
  };

  useEffect(() => {
    const makeNote = async () => {
      const note = await (
        await fetch(`/api/note/${props.match.params.id}`)
      ).json();
      setEntry(note);
    };
    if (props.match.params.id !== "new") {
      makeNote();
    }
  }, [props.match.params.id]);

  const title = <h2>{entry.id ? "Edit Group" : "Add Group"}</h2>;

  return (
    <div>
      <AppNavbar />
      <Container>
        {title}
        <Form onSubmit={(e) => handleSubmit(e)}>
          <FormGroup>
            <Label for="title">Title</Label>
            <Input
              type="text"
              name="title"
              id="title"
              value={entry.title || ""}
              onChange={(e) => handleChange(e)}
            />
          </FormGroup>
          <FormGroup>
            <Label for="author">Author</Label>
            <Input
              type="text"
              name="author"
              id="author"
              value={entry.author || ""}
              onChange={(e) => handleChange(e)}
            />
          </FormGroup>
          <FormGroup>
            <Label for="text">Text</Label>
            <Input
              type="text"
              name="text"
              id="text"
              value={entry.text || ""}
              onChange={(e) => handleChange(e)}
            />
          </FormGroup>
          <FormGroup>
            <Button color="primary" type="submit">
              Save
            </Button>{" "}
            <Button color="secondary" tag={Link} to="/notes">
              Cancel
            </Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  );
};

export default withRouter(NoteEdit);
