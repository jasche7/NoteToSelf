import AppNavbar from "../AppNavbar";
import { render } from "@testing-library/react";
import { StaticRouter } from "react-router-dom";

describe(`The AppNavbar component`, () => {
  it(`can return to root by clicking "Home"`, () => {
    const { getByText } = render(
      <StaticRouter>
        <AppNavbar />
      </StaticRouter>
    );

    expect(getByText("Home").closest("a")).toHaveAttribute("href", "/");
  });
});
