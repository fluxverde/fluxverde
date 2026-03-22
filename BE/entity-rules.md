# Entity Class Rules

## 1. serialVersionUID
- All entity classes that implement `Serializable` must declare a `private static final long serialVersionUID` field.
- This ensures serialization compatibility and prevents runtime errors during deserialization.

## 2. @Column name Attribute
- Do not use the `name = ...` attribute in the `@Column` annotation if the column name is identical to the Java field name.
- Only specify the `name` attribute if the column name differs from the field name or if additional attributes (e.g., `unique`, `length`) are required.

## 3. Copilot Execution Rules
- Copilot may run `./mvnw clean compile -DskipTests` without asking for confirmation.
- After each non-trivial code change, Copilot should run a compile check.
- Copilot should ask before destructive commands (for example: deleting files, hard resets, force pushes).

---

These rules should be followed for all entity classes in this project to ensure consistency and best practices.
