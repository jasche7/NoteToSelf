import Home from "../Home";
import { render } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import userEvent from "@testing-library/user-event";

describe(`The Home component`, () => {
  let assignMock = jest.fn();

  delete window.location;
  window.location = { assign: assignMock };

  afterEach(() => {
    assignMock.mockClear();
  });

  it(`has a login button if no user is logged in`, () => {
    const { getByText } = render(
      <MemoryRouter>
        <Home />
      </MemoryRouter>
    );

    const loginButton = getByText("Login");
    userEvent.click(loginButton);

    expect(window.location.href).toContain("/private");
  });
});
