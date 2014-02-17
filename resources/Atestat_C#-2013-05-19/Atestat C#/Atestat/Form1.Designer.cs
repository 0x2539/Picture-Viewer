namespace Atestat
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.dateText = new System.Windows.Forms.RichTextBox();
            this.imgTablou = new System.Windows.Forms.PictureBox();
            this.btnPictor = new System.Windows.Forms.Button();
            this.btnTablou = new System.Windows.Forms.Button();
            this.pictureBox2 = new System.Windows.Forms.PictureBox();
            ((System.ComponentModel.ISupportInitialize)(this.imgTablou)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox2)).BeginInit();
            this.SuspendLayout();
            // 
            // dateText
            // 
            this.dateText.BorderStyle = System.Windows.Forms.BorderStyle.None;
            this.dateText.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.dateText.Location = new System.Drawing.Point(282, 12);
            this.dateText.Name = "dateText";
            this.dateText.Size = new System.Drawing.Size(350, 360);
            this.dateText.TabIndex = 0;
            this.dateText.Text = "";
            // 
            // imgTablou
            // 
            this.imgTablou.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.imgTablou.Location = new System.Drawing.Point(12, 12);
            this.imgTablou.Name = "imgTablou";
            this.imgTablou.Size = new System.Drawing.Size(250, 250);
            this.imgTablou.SizeMode = System.Windows.Forms.PictureBoxSizeMode.Zoom;
            this.imgTablou.TabIndex = 1;
            this.imgTablou.TabStop = false;
            // 
            // btnPictor
            // 
            this.btnPictor.AutoSize = true;
            this.btnPictor.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.btnPictor.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnPictor.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnPictor.Location = new System.Drawing.Point(60, 309);
            this.btnPictor.Name = "btnPictor";
            this.btnPictor.Size = new System.Drawing.Size(54, 28);
            this.btnPictor.TabIndex = 2;
            this.btnPictor.Text = "Pictor";
            this.btnPictor.UseVisualStyleBackColor = true;
            this.btnPictor.Click += new System.EventHandler(this.btnPictor_Click);
            // 
            // btnTablou
            // 
            this.btnTablou.AutoSize = true;
            this.btnTablou.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.btnTablou.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnTablou.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnTablou.Location = new System.Drawing.Point(136, 309);
            this.btnTablou.Name = "btnTablou";
            this.btnTablou.Size = new System.Drawing.Size(63, 28);
            this.btnTablou.TabIndex = 3;
            this.btnTablou.Text = "Tablou";
            this.btnTablou.UseVisualStyleBackColor = true;
            this.btnTablou.Click += new System.EventHandler(this.btnTablou_Click);
            // 
            // pictureBox2
            // 
            this.pictureBox2.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.pictureBox2.Location = new System.Drawing.Point(281, 11);
            this.pictureBox2.Name = "pictureBox2";
            this.pictureBox2.Size = new System.Drawing.Size(352, 362);
            this.pictureBox2.TabIndex = 4;
            this.pictureBox2.TabStop = false;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(641, 383);
            this.Controls.Add(this.btnTablou);
            this.Controls.Add(this.btnPictor);
            this.Controls.Add(this.imgTablou);
            this.Controls.Add(this.dateText);
            this.Controls.Add(this.pictureBox2);
            this.Name = "Form1";
            this.Text = "Atestat";
            ((System.ComponentModel.ISupportInitialize)(this.imgTablou)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox2)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.RichTextBox dateText;
        private System.Windows.Forms.PictureBox imgTablou;
        private System.Windows.Forms.Button btnPictor;
        private System.Windows.Forms.Button btnTablou;
        private System.Windows.Forms.PictureBox pictureBox2;
    }
}

