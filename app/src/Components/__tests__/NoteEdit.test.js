import NoteEdit from "../NoteEdit";
import { render } from "@testing-library/react";
import { StaticRouter } from "react-router-dom";

describe(`The NoteEdit component`, () => {
  it(`should have a form for inputting title and text for a Note and a button to submit the form`, () => {
    const { getByLabelText, getByText } = render(
      <StaticRouter>
        <NoteEdit />
      </StaticRouter>
    );

    expect(getByLabelText("Title").closest("Input")).toHaveAttribute(
      "id",
      "title"
    );
    expect(getByLabelText("Text").closest("Input")).toHaveAttribute(
      "id",
      "text"
    );
    expect(getByText("Save").closest("Button")).toHaveAttribute(
      "type",
      "submit"
    );
  });

  it(`should take the user back to the Notes page after clicking cancel`, () => {
    const { getByText } = render(
      <StaticRouter>
        <NoteEdit />
      </StaticRouter>
    );

    expect(getByText("Cancel").closest("a")).toHaveAttribute("href", "/notes");
  });
});
