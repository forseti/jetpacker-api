---
- hosts: all
  roles:
    - commons
    - containers

- hosts: all
  become_user: vagrant
  roles:
    - kits