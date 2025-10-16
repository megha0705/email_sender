
tinymce.init({
  selector: '#email-body',
  height: 300,
  menubar: false,
  plugins: 'link lists image table code',
  toolbar: 'undo redo | bold italic underline | bullist numlist | link | code',
  branding: false
});


function previewEmail() {
  const subject = document.getElementById("email-subject").value;
  const body = tinymce.get('email-body').getContent();

  if (!subject && !body) {
    alert("Please enter subject and body to preview.");
    return;
  }

  const previewWindow = window.open("", "_blank", "width=800,height=600");
  previewWindow.document.write(`<h2>${subject}</h2>`);
  previewWindow.document.write(body);
}


const params = new URLSearchParams(window.location.search);
const fileId = params.get("fileId");

if (fileId) {
  const fileInput = document.getElementById("campaign-file");
  const fileLabel = document.getElementById("existing-file");
  const createButton = document.querySelector("button.action");

  fileInput.disabled = true;
  //createButton.textContent = "Reschedule Campaign";


  fetch(`http://localhost:8080/{fileId}/getLogsById`)
    .then(res => res.json())
    .then(data => {
      fileLabel.textContent = `📄 Selected file: ${data.fileName}`;


      if (data.scheduledTime) {
        document.getElementById("campaign-date").value = data.scheduledTime.replace(" ", "T");
      }


      if (data.emailContent) {
        document.getElementById("email-subject").value = data.emailContent.subject;
        tinymce.get('email-body').setContent(data.emailContent.body);
      }
    })
    .catch(err => {
      console.error("Error fetching file details:", err);
      fileLabel.textContent = "⚠️ Could not load file details.";
    });
}


async function createCampaign() {
  const file = document.getElementById("campaign-file").files[0];
  const date = document.getElementById("campaign-date").value;
  const subject = document.getElementById("email-subject").value;
  const body = tinymce.get('email-body').getContent();
  const errorEl = document.getElementById("campaign-error");
  const responseEl = document.getElementById("campaign-response");

  errorEl.textContent = "";
  responseEl.textContent = "";


  if ((!fileId && !file) || !date || !subject || !body) {
    errorEl.textContent = "⚠️ Please fill all required fields.";
    return;
  }

  const formData = new FormData();
  formData.append("dateTime", date);
  formData.append("subject", subject);
  formData.append("body", body);

  let endpoint = "http://localhost:8080/campaign/create";

  if (fileId) {
    endpoint = "http://localhost:8080/campaign/reschedule";
    formData.append("fileId", Number(fileId));
  } else {
    formData.append("file", file);
  }

  try {
    const response = await fetch(endpoint, {
      method: "POST",
      body: formData
    });

    if (response.ok) {
      const text = await response.text();
      responseEl.textContent = `✅ ${text}`;
      setTimeout(() => { window.location.href = "../FileLog/FileLog.html"; }, 1500);
    } else {
      errorEl.textContent = "❌ Failed to create campaign.";
    }
  } catch (err) {
    console.error("Error:", err);
    errorEl.textContent = "❌ Something went wrong while connecting to backend.";
  }
}
