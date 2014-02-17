using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace Atestat
{
    public partial class Form1 : Form
    {
        string path = "";
        public Form1(string[] args)
        {
            InitializeComponent();

            if (args.Length > 0)
            {
                path = args[0];
                loadTextTablou();
                loadImageTablou();
            }
            //in java ca sa pornim un exe//Process process = new ProcessBuilder("C:\\PathToExe\\MyExe.exe","param1","param2").start();
        }

        public void loadImageTablou()
        {
            try
            {
                imgTablou.Load(Application.StartupPath + "\\Imagini\\page" + path + ".png");
            }
            catch (Exception ex)
            {
            }
        }

        public void loadTextTablou()
        {
            try
            {
                dateText.LoadFile(Application.StartupPath + "\\Date\\date" + path + "1.txt", RichTextBoxStreamType.PlainText);
            }
            catch (Exception ex)
            {
            }
        }

        public void loadTextPictor()
        {
            try
            {
                dateText.LoadFile(Application.StartupPath + "\\Date\\date" + path + "2.txt", RichTextBoxStreamType.PlainText);
            }
            catch (Exception ex)
            {
            }
        }

        private void btnPictor_Click(object sender, EventArgs e)
        {
            try
            {
                loadTextPictor();
            }
            catch (Exception ex)
            {
            }
        }

        private void btnTablou_Click(object sender, EventArgs e)
        {
            try
            {
                loadTextTablou();
            }
            catch (Exception ex)
            {
            }
        }
    }
}
