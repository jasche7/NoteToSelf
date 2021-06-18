import { useState } from "react";
import { Navbar, NavbarBrand, NavbarToggler } from "reactstrap";
import { Link } from "react-router-dom";

const AppNavbar = () => {
  const [isOpen, setOpen] = useState(false);
  const toggle = () => {
    setOpen(!isOpen);
  };

  return (
    <Navbar color="dark" dark expand="md">
      <NavbarBrand tag={Link} to="/">
        Home
      </NavbarBrand>
      <NavbarToggler onClick={toggle} />
    </Navbar>
  );
};

export default AppNavbar;
