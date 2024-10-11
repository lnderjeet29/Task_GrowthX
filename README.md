<h1>Task Submission Portal Backend</h1>

<p align="center">
  <img src="https://github.com/user-attachments/assets/eeac5610-184a-48dc-9b03-bbdbb659bdbb" alt="GrowthX Logo" width="400" height="200"/>
</p>
<h2>Overview</h2>
<p>This is the backend service for an <strong>Assignment Submission Portal</strong> that allows users to submit assignments and admins to review them. The system includes two types of users: <strong>Users</strong> and <strong>Admins</strong>. Users can register, log in, and upload assignments, while admins can register, log in, and manage the assignments submitted by users. The system is secured by <strong>JWT-based authentication</strong>, ensuring that only authorized users and admins can access specific endpoints.</p>

<h2>Features</h2>
<ul>
  <li><strong>User Registration and Login</strong>: Users can register and log in to the system.</li>
  <li><strong>Admin Registration and Login</strong>: Admins can register and manage assignments submitted by users.</li>
  <li><strong>JWT Authentication</strong>: All user and admin actions are secured via JWT.</li>
  <li><strong>Assignment Upload</strong>: Users can upload assignments.</li>
  <li><strong>Admin Actions</strong>: Admins can accept or reject assignments submitted by users.</li>
</ul>

<h2>Technologies Used</h2>
<ul>
  <li><strong>Spring Boot</strong>: Backend framework.</li>
  <li><strong>Spring Security</strong>: For securing endpoints.</li>
  <li><strong>JWT</strong>: For authentication.</li>
  <li><strong>MongoDB</strong>: Database to store user and assignment information.</li>
  <li><strong>Lombok</strong>: For reducing boilerplate code.</li>
  <li><strong>Maven</strong>: Dependency management.</li>
</ul>

<h2>Prerequisites</h2>
<ul>
  <li><strong>Java 17 or later</strong> installed on your machine.</li>
  <li><strong>MongoDB</strong> server running locally or on a remote machine.</li>
  <li><strong>Maven</strong> installed for managing dependencies and building the project.</li>
</ul>

<h2>Setup and Installation</h2>

<ol>
  <li>Clone the repository:
    <pre><code>git clone https://github.com/lnderjeet29/Task_GrowthX.git</code></pre>
  </li>
  <li>Navigate into the project directory:
    <pre><code>cd Task_GrowthX</code></pre>
  </li>
  <li>Install the required dependencies using Maven:
    <pre><code>mvn clean install</code></pre>
  </li>
  <li>Configure the MongoDB connection in <code>src/main/resources/application.properties</code>:
    <pre><code>spring.data.mongodb.uri=mongodb://localhost:27017/task_db</code></pre>
  </li>
  <li>Run the Spring Boot application:
    <pre><code>mvn spring-boot:run</code></pre>
  </li>
  <li>The application will start on <a href="http://localhost:8080">http://localhost:8080</a>.
  </li>
</ol>

<h2>API Endpoints</h2>

<h3>User Endpoints</h3>

<table>
  <thead>
    <tr>
      <th>HTTP Method</th>
      <th>Endpoint</th>
      <th>Description</th>
      <th>Authentication Required</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>POST</td>
      <td>/user/register</td>
      <td>Registers a new user.</td>
      <td>No</td>
    </tr>
    <tr>
      <td>POST</td>
      <td>/user/login</td>
      <td>Logs in the user and returns a JWT token.</td>
      <td>No</td>
    </tr>
    <tr>
      <td>POST</td>
      <td>/user/upload</td>
      <td>Submits an assignment for the logged-in user.</td>
      <td>Yes (JWT Token Required)</td>
    </tr>
    <tr>
      <td>GET</td>
      <td>/user/admins</td>
      <td>Fetches all registered admins.</td>
      <td>Yes (JWT Token Required)</td>
    </tr>
  </tbody>
</table>

<h3>Admin Endpoints</h3>

<table>
  <thead>
    <tr>
      <th>HTTP Method</th>
      <th>Endpoint</th>
      <th>Description</th>
      <th>Authentication Required</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>POST</td>
      <td>/admin/register</td>
      <td>Registers a new admin.</td>
      <td>No</td>
    </tr>
    <tr>
      <td>GET</td>
      <td>/admin/assignments</td>
      <td>Fetches all assignments submitted for the admin.</td>
      <td>Yes (JWT Token Required)</td>
    </tr>
    <tr>
      <td>POST</td>
      <td>/admin/assignments/{id}/accept</td>
      <td>Accepts an assignment by ID.</td>
      <td>Yes (JWT Token Required)</td>
    </tr>
    <tr>
      <td>POST</td>
      <td>/admin/assignments/{id}/reject</td>
      <td>Rejects an assignment by ID.</td>
      <td>Yes (JWT Token Required)</td>
    </tr>
  </tbody>
</table>

<h2>JWT Authentication Flow</h2>
<p>After registering, users or admins can log in using their credentials. Upon successful login, a JWT token is generated and returned.</p>
<p>The JWT token must be included in the <code>Authorization</code> header of all subsequent requests that require authentication. Example:</p>

<pre><code>Authorization: Bearer &lt;JWT_TOKEN&gt;</code></pre>

<h2>Example Requests</h2>

<h3>Register User</h3>

<pre><code>POST /user/register
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123"
}
</code></pre>

<h3>Login User</h3>

<pre><code>POST /user/login
Content-Type: application/json

{
  "user_name": "john@example.com",
  "password": "password123"
}
</code></pre>

<h3>Upload Assignment</h3>

<pre><code>POST /user/upload
Authorization: Bearer &lt;JWT_TOKEN&gt;
Content-Type: application/json

{
  "task": "Complete the backend assignment",
  "adminId": "admin123"
}
</code></pre>

<h3>Get Assignments for Admin</h3>

<pre><code>GET /admin/assignments
Authorization: Bearer &lt;JWT_TOKEN&gt;
</code></pre>

<h3>Accept Assignment</h3>

<pre><code>POST /admin/assignments/{id}/accept
Authorization: Bearer &lt;JWT_TOKEN&gt;
</code></pre>

<h2>Exception Handling</h2>
<ul>
  <li><strong>User Already Exists</strong>: Returns <code>409 Conflict</code> if a user or admin with the same email already exists during registration.</li>
  <li><strong>Invalid Credentials</strong>: Returns <code>401 Unauthorized</code> if login credentials are invalid.</li>
  <li><strong>Assignment Errors</strong>: Returns <code>500 Internal Server Error</code> if there are issues submitting or processing an assignment.</li>
</ul>

<h2>Future Enhancements</h2>
<ul>
  <li><strong>OAuth2 Support</strong>: Add support for third-party authentication like Google or GitHub.</li>
  <li><strong>Pagination</strong>: Add pagination for listing assignments when the admin retrieves them.</li>
  <li><strong>Assignment Comments</strong>: Allow admins to leave comments or feedback on assignments.</li>
</ul>

<h2>License</h2>
<p>This project is licensed under the MIT License - see the <a href="LICENSE">LICENSE</a> file for details.</p>
