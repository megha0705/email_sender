async function fetchEmailLogs() {
  const params = new URLSearchParams(window.location.search);
  const fileId = params.get("fileId");

  try {

    const response = await fetch(`/${fileId}/getLogsById`);
    const emailLogs = await response.json();

    const tableBody = document.getElementById("email-logs-table");
    tableBody.innerHTML = "";


    if (Array.isArray(emailLogs)) {
      emailLogs.forEach(log => {
        const row = `<tr>
          <td>${log.id}</td>
          <td>${log.emailId}</td>
          <td class="status-${log.status}">${log.status}</td>
          <td>${log.logTime || '-'}</td>
          <td>${log.errorMsg || '-'}</td>
        </tr>`;
        tableBody.innerHTML += row;
      });
    } else {
      console.error("Unexpected response:", emailLogs);
    }
  } catch (error) {
    console.error("Error fetching email logs:", error);
  }
}


fetchEmailLogs();
