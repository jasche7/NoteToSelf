import { Nav, Navbar, NavbarBrand, NavItem, NavLink } from "reactstrap";
import { Link } from "react-router-dom";

const AppNavbar = () => {
  return (
    <Navbar color="dark" dark expand="md">
      <NavbarBrand tag={Link} to="/">
        Home
      </NavbarBrand>
      <Nav className="ml-auto" navbar>
        <NavItem>
          <NavLink href="https://github.com/jasche7/NoteToSelf">GitHub</NavLink>
        </NavItem>
      </Nav>
    </Navbar>
  );
};

export default AppNavbar;
