import NoteList from "../NoteList";
import { render } from "@testing-library/react";
import { StaticRouter } from "react-router-dom";

describe(`The NoteList component`, () => {
  it(`should give a loading message if not loaded`, () => {
    const { getByText } = render(
      <StaticRouter>
        <NoteList />
      </StaticRouter>
    );

    expect(getByText("Loading...")).toBeInTheDocument();
  });

  describe(`, if loaded,`, () => {
    it(`should have a button to add a new Note`, () => {
      const { getByText } = render(
        <StaticRouter>
          <NoteList debug={true} />
        </StaticRouter>
      );

      expect(getByText("Add Note").closest("a")).toHaveAttribute(
        "href",
        "/note/new"
      );
    });
  });
});
