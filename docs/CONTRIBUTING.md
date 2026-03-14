# Contributing Guidelines

Jeenify – Student Attendance & Assessment Management System

This document explains how team members should contribute to the Jeenify project and follow the correct development workflow.

---

## 1. Repository Structure

The project repository is organized as follows:

```bash
jeenify
│
├── frontend
│   ├── html
│   ├── css
│   └── js
│
├── backend
│   ├── controllers
│   ├── services
│   ├── models
│   └── config
│
├── database
│   ├── schema.sql
│   └── sample_data.sql
│
├── docs
│   ├── PROJECT_REQUIREMENTS.md
│   ├── SYSTEM_ARCHITECTURE.md
│   ├── DATABASE_SCHEMA.md
│   ├── SYSTEM_DESIGN.md
│   ├── API_SPECIFICATION.md
│   ├── TEAM_STRUCTURE.md
│   └── ROADMAP.md
│
└── tests
```

Each team should only work within the relevant module folder.

---

## 2. Branch Structure

The repository follows this branch structure:

main
develop
frontend
backend
database
docs
testing

### Branch Purposes

main

- Stable version of the project
- Final production-ready code

develop

- Integration branch
- All modules are merged here before final release

frontend

- UI development

backend

- Java backend development

database

- Database schema and queries

docs

- Documentation updates

testing

- Testing and bug fixes

---

## 3. Development Workflow

All development must follow this workflow:

Feature Branch
      ↓
Module Branch (frontend/backend/database)
      ↓
Develop Branch
      ↓
Main Branch

Step-by-step workflow

1. Pull the latest changes

git pull origin develop

2. Switch to your module branch

Example:

git checkout frontend

3. Create a feature branch

Example:

git checkout -b feature-login-page

4. Work on the feature

5. Commit your changes

git add .
git commit -m "Added login page UI"

6. Push the feature branch

git push origin feature-login-page

7. Create a Pull Request to the module branch

Example:

feature-login-page → frontend

8. After review, the module branch is merged into develop.

9. When the system becomes stable, develop is merged into main.

---

## 4. Pull Request Guidelines

Every feature must be submitted through a Pull Request.

Pull requests should include:

- Clear description of the feature
- Related issue or task
- Screenshots if UI changes are made

Pull requests must be reviewed before merging.

Only maintainers should merge pull requests.

---

## 5. Commit Message Guidelines

Write clear and meaningful commit messages.

Good Examples

Added student registration form
Implemented attendance API
Created timetable management page
Fixed login authentication bug

Avoid

update
changes
done
fix

---

## 6. Coding Standards

To maintain code quality, follow these rules:

- Write clean and readable code
- Use meaningful variable names
- Add comments where necessary
- Follow consistent naming conventions
- Avoid unnecessary code duplication

---

## 7. Collaboration Guidelines

Team members should:

- Work only on assigned tasks
- Avoid modifying other modules unnecessarily
- Communicate with team leads before major changes
- Report bugs immediately

All communication should happen through:

- GitHub Issues
- Team discussion channels

---

## 8. Issue Reporting

If a bug is found:

1. Create a GitHub Issue
2. Describe the problem
3. Include reproduction steps
4. Attach screenshots if necessary

The testing team will verify the issue before it is fixed.

---

## 9. Code Review

Before merging any feature:

- Code must be reviewed
- Conflicts must be resolved
- Tests must pass
- Documentation must be updated if needed

---

## 10. Final Integration

Before merging into main, the system should be fully tested.

This includes:

- Authentication testing
- Attendance workflow testing
- Assessment workflow testing
- Timetable functionality
- Academic calendar functionality
- Dashboard functionality

---

## 11. Contribution Principles

To ensure smooth collaboration:

- Follow the Git workflow
- Commit frequently
- Write clear commit messages
- Test your code before pushing
- Keep the repository organized
